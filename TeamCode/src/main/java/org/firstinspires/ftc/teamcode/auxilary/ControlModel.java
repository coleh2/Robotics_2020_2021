package org.firstinspires.ftc.teamcode.auxilary;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.ControlMap;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class ControlModel {
    public HashMap<String, Control> controls;
    public ControlModelVariables variables;

    public ControlModel(ControlMap map) {
        this.controls = getControlFields(map.getClass());
    }

    private HashMap<String, Control> getControlFields(Class mapClass) {
        HashMap<String, Control> result = new HashMap<String, Control>();
        Field[] allDeclaredFields = mapClass.getDeclaredFields();

        for(Field field : allDeclaredFields) {
            if(field.getType().equals(String.class)) {
                try {
                    Control thisControl = parseField(field);
                    result.put(thisControl.name, thisControl);
                } catch (Exception err) {
                    FeatureManager.logger.add("WARNING: Unparsable control field `" + field.getName() + "` in control map `" + mapClass.getName() + "`: " + err.getMessage());
                }
            }
        }
        return result;
    }

    public Control get(String name) {
        Control control = controls.get(name);
        return control;
    }

    private void updateVariables(GamepadState state) {
        for(ControlModelVariables.ControlModelVariable variable : variables.values()) {
            for(String setterName : variable.setters) this.get(setterName).res(state, false);
        }
    }

    private Control parseField(Field field) throws IllegalAccessException, IllegalArgumentException {
        String fieldName = field.getName();
        String fieldValue = field.get(null).toString();

        Control result = new Control(this);
        result.setName(fieldName);
        result.parse(fieldValue);

        return result;
    }

    public static class ControlModelVariables extends LinkedHashMap<String, ControlModelVariables.ControlModelVariable> {
        private static class ControlModelVariable {
            public float[] value;
            public ArrayList<String> setters;

            public ControlModelVariable() {
                this.setters = new ArrayList<String>();
            }
        }

        public int register(String variableName) {
            if(!this.containsKey(variableName)) {
                ControlModelVariables.ControlModelVariable var = new ControlModelVariables.ControlModelVariable();
                this.put(variableName, var);
            }

            return this.indexOf(variableName);
        }

        public int register(String variableName, String setterName) {
            this.register(variableName);
            ControlModelVariable c = this.get(variableName);
            if(c != null) c.setters.add(setterName);

            return this.indexOf(variableName);
        }

        public int indexOf(String variableName) {
            String[] keys = this.keySet().toArray(new String[0]);
            for(int i = 0; i < keys.length; i++) {
                if(keys[i].equals(variableName)) return i;
            }
            return -1;
        }
        public void put(int idx, float[] val) {
            if(idx >= this.size()) throw new IllegalArgumentException();

            Set<String> entries = this.keySet();
            String key = entries.toArray(new String[0])[idx];

            ControlModelVariables.ControlModelVariable oldValue = this.get(key);

            if(oldValue == null) throw new IllegalArgumentException("Unregistered index " + idx + "attempted to be set");
            oldValue.value = val;

            this.put(key, oldValue);
        }

        public float[] get(int idx) {
            if(idx >= this.size()) throw new IllegalArgumentException();

            Set<String> entries = this.keySet();
            String key = entries.toArray(new String[0])[idx];

            ControlModelVariables.ControlModelVariable value = this.get(key);
            if(value == null) throw new IllegalArgumentException("Unregistered index " + idx + "attempted to be get");
            return value.value;
        }
    }

    public static class Control {
        public String name;
        public ControlType type;
        private ControlModel model;
        public Control[] children;
        public int state;
        public float value;
        public Control() { state = 0; }

        public Control(ControlModel mdl) {
            this.model = mdl;
            this.state = 0;
        }

        public Control(String src, ControlModel mdl) {
            this.state = 0;
            this.name = "";
            this.model = mdl;

            this.parse(src);
        }

        public boolean containsVariableAccess() {
            if(this.type.equals(ControlType.GET_VARIABLE)) return true;
            for (Control child: children) {
                if(child.containsVariableAccess()) return true;
            }
            return false;
        }

        public void parse(String src) {
            String[] tokens = src.split("[^\\w\\d.]+");
            int pointer = 0;
            if(tokens[pointer].equals("")) pointer++;

            if(tokens[pointer].matches("[\\d.]+")) {
                this.type = ControlType.LITERAL;
                //inline literals mean that it has to be stepped back 1 token in order to not skip
                pointer--;
            }
            else this.type = ControlType.valueOf(PaulMath.camelToSnake(tokens[pointer]));

            this.children = new Control[type.paramCount];

            int paramsToGo = type.paramCount;
            int lastTopLevelParam = type.paramCount;

            //literals don't get all the parsing. Neither do variables.
            if(type == ControlType.LITERAL) {
                pointer++;
                this.value = Float.parseFloat(tokens[pointer]);
                return;
            }
            if(type == ControlType.GET_VARIABLE) {
                pointer++;
                String varName = tokens[pointer];
                this.value = model.variables.register(varName);
                return;
            } else if(type == ControlType.SET_VARIABLE) {
                pointer++;
                String varName = tokens[pointer];
                this.value = model.variables.register(varName, this.name);
                return;
            }
            StringBuilder srcSoFar = new StringBuilder();
            //find the end of this method, with all variables
            for(int i = pointer + 1; paramsToGo > 0; i++) {
                ControlType thisTokenType = ControlType.valueOf(PaulMath.camelToSnake(tokens[i]));
                paramsToGo += thisTokenType.paramCount;
                paramsToGo--;
                srcSoFar.append(" ").append(tokens[i]);
                //if we're done with the parameter, parse the total source
                if(paramsToGo < lastTopLevelParam) {
                    children[type.paramCount - lastTopLevelParam] = new Control(srcSoFar.toString(), model);
                    //inherit the name from parent
                    children[type.paramCount - lastTopLevelParam].setName(name);
                    lastTopLevelParam = paramsToGo;
                    srcSoFar = new StringBuilder();
                }
            }
        }

        public float[] res(GamepadState state) {
            return res(state, true);
        }

        public float[] res(GamepadState state, boolean recurse) {
            //update variables if we need them
            if(this.containsVariableAccess() && recurse) model.updateVariables(state);

            //simple input method
            if(type.subtype.equals("InputMethod")) {
                String inKey = type.name().toLowerCase();
                return new float[]{state.getButtonState(inKey)};
            } else {
                switch(type) {
                    case SCALAR:
                        return new float[]{children[0].res(state)[0]};
                    case VECTOR2:
                        return new float[]{
                                children[0].res(state)[0],
                                children[1].res(state)[0]};
                    case VECTOR3:
                        return new float[]{
                                children[0].res(state)[0],
                                children[1].res(state)[0],
                                children[2].res(state)[0]};
                    case VECTOR4:
                        return new float[]{
                                children[0].res(state)[0],
                                children[1].res(state)[0],
                                children[2].res(state)[0],
                                children[3].res(state)[0]};
                    case TOGGLE:
                        boolean currentState = this.state != 0;
                        //only on rising edge so that it doesn't toggle onoffonoffonoff when people press a button
                        if(children[0].res(state)[0] != 0 && children[0].res(state.history)[0] == 0) currentState = !currentState;
                        this.state = currentState?1:0;

                        return new float[]{(float) this.state};
                    case HOLD:
                        boolean res = children[0].res(state)[0] != 0;

                        return new float[]{res?1f:0f};
                    case PUSH:
                        //rising edge only
                        return new float[]{
                            (children[0].res(state)[0] != 0 && children[0].res(state.history)[0] == 0)?1f:0f
                        };
                    case COMBO:
                        if(children[0].res(state)[0] != 0) return children[1].res(state);
                        else return children[0].res(state);
                    case TOGGLE_BETWEEN:
                        boolean currentTbState = this.state != 0;
                        if(children[0].res(state)[0] != 0 && children[0].res(state.history)[0] == 0) currentTbState = !currentTbState;
                        this.state = currentTbState?1:0;

                        if(currentTbState) return children[1].res(state);
                        else return children[1].res(state);
                    case NOT:
                        return new float[]{
                                (children[0].res(state)[0]==0)?1f:0f
                        };
                    case TERNARY:
                    case IF:
                        if(children[0].res(state)[0] != 0) return children[1].res(state);
                        else return children[2].res(state);
                    case SCALE:
                        float[] toScale = children[0].res(state);
                        float scaleFactor = children[1].res(state)[0];
                        for(int i = 0; i < toScale.length; i++) {
                            toScale[i] *= scaleFactor;
                        }

                        return toScale;
                    case LITERAL:
                        return new float[]{this.value};
                    case SET_VARIABLE:
                        float[] variableSetValue = children[0].res(state);
                        model.variables.put((int)this.value, variableSetValue);
                        return variableSetValue;
                    case GET_VARIABLE:
                        return model.variables.get((int)this.value);
                    case DUMMY:
                        return children[0].res(state);
                    case GREATER_THAN:
                        return new float[] { children[0].res(state)[0] > children[0].res(state)[1] ? 1f : 0f};
                    case LESS_THAN:
                        return new float[] { children[0].res(state)[0] < children[0].res(state)[1] ? 1f : 0f};
                    case NULL:
                    default:
                        return new float[]{0f};
                }
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ControlType getType() {
            return type;
        }

        public void setType(ControlType type) {
            this.type = type;
        }

        public Control[] getChildren() {
            return children;
        }

        public void setInputs(Control[] children) {
            this.children = children;
        }

        public String toString() {
            StringBuilder paramsAsStrings = new StringBuilder();
            for(int i = 0; i < children.length; i++) {
                paramsAsStrings.append(children[i].toString());
                if(i + 1 < children.length) paramsAsStrings.append(", ");
            }
            return this.type.toString() + (this.type.paramCount > 0 ? "(" + paramsAsStrings + ")" : "");
        }
    }

    public enum ControlType {
        DPAD_LEFT("InputMethod", 0), DPAD_RIGHT("InputMethod", 0), DPAD_DOWN("InputMethod", 0),
        DPAD_UP("InputMethod", 0), A("InputMethod", 0), X("InputMethod", 0), B("InputMethod", 0),
        Y("InputMethod", 0), LEFT_BUMPER("InputMethod", 0), RIGHT_BUMPER("InputMethod", 0),
        LEFT_TRIGGER("InputMethod", 0), RIGHT_TRIGGER("InputMethod", 0), LEFT_STICK_X("InputMethod", 0),
        LEFT_STICK_Y("InputMethod", 0), RIGHT_STICK_X("InputMethod", 0), RIGHT_STICK_Y("InputMethod", 0),

        SCALAR("OutputType", 1), VECTOR2("OutputType", 2), VECTOR3("OutputType", 3), VECTOR4("OutputType", 4),
        TOGGLE("OutputType", 1), HOLD("OutputType", 1), PUSH("OutputType", 1), COMBO("OutputType", 2),
        TOGGLE_BETWEEN("OutputType", 3),

        NOT("Middleware", 1), TERNARY("Middleware", 3), IF("Middleware", 3), SCALE("Middleware", 2),
        DUMMY("Middleware", 1), NULL("Middleware", 0), LITERAL("Middleware", 1), SET_VARIABLE("Middleware", 1),
        GET_VARIABLE("Middleware", 0), GREATER_THAN("Middleware", 2), LESS_THAN("Middleware", 2);

        public String subtype;
        public int paramCount;
        public boolean isTerminal;
        ControlType(String subtype, int paramCount) {
            this.subtype = subtype;
            this.paramCount = paramCount;
            this.isTerminal = paramCount == 0;
        }
    }

    public float[] findMidpoint(float x1, float y1, float x2, float y2)  {
        return new float[]{x2 + (x1 - x2), y2 + (y1 - y2)};
    }
}