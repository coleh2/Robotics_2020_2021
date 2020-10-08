package org.firstinspires.ftc.teamcode.auxilary;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.ControlMap;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ControlModel {
    public HashMap<String, Control> controls;

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
        return controls.get(name);
    }

    private Control parseField(Field field) throws IllegalAccessException, IllegalArgumentException {
        String fieldName = field.getName();
        String fieldValue = field.get(null).toString();

        Control result = new Control();
        result.setName(fieldName);
        result.parse(fieldValue);

        return result;
    }

    public class Control {
        public String name;
        public ControlType type;
        public Control[] children;
        public int state;
        public float value;
        public Control() { state = 0; }

        public Control(String src) {
            this.state = 0;
            this.name = "";
            this.parse(src);
        }

        public void parse(String src) {
            String[] tokens = src.split("[^\\w\\d.]+");
            int pointer = 0;
            if(tokens[pointer].equals("")) pointer++;
            this.type = ControlType.valueOf(PaulMath.camelToSnake(tokens[pointer]));
            this.children = new Control[type.paramCount];

            int paramsToGo = type.paramCount;
            int lastTopLevelParam = type.paramCount;

            //literals don't get all the parsing
            if(type == ControlType.LITERAL) {
                pointer++;
                this.value = Float.parseFloat(tokens[pointer]);
                return;
            }
            String srcSoFar = "";
            //find the end of this method, with all variables
            for(int i = pointer + 1; paramsToGo > 0; i++) {
                System.out.println(paramsToGo);
                ControlType thisTokenType = ControlType.valueOf(PaulMath.camelToSnake(tokens[i]));
                paramsToGo += thisTokenType.paramCount;
                paramsToGo--;
                srcSoFar += " " + tokens[i];
                //if we're done with the parameter, parse the total source
                if(paramsToGo < lastTopLevelParam) {
                    children[type.paramCount - lastTopLevelParam] = new Control(srcSoFar);
                    lastTopLevelParam = paramsToGo;
                    srcSoFar = "";
                }
            }
        }

        public float[] res(GamepadState state) {
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
                        else return new float[]{0f};
                    case TOGGLEBETWEEN:
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
                        else return children[1].res(state);
                    case SCALE:
                        float[] toScale = children[1].res(state);
                        float scaleFactor = children[0].res(state)[0];
                        for(int i = 0; i < toScale.length; i++) {
                            toScale[i] *= scaleFactor;
                        }

                        return toScale;
                    case LITERAL:
                        return new float[]{this.value};
                    case DUMMY:
                        return children[0].res(state);
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
            String paramsAsStrings = "";
            for(int i = 0; i < children.length; i++) {
                paramsAsStrings += children[i].toString();
                if(i + 1 < children.length) paramsAsStrings += ", ";
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
        TOGGLEBETWEEN("OutputType", 3),

        NOT("Middleware", 1), TERNARY("Middleware", 3), IF("Middleware", 3), SCALE("Middleware", 2),
        DUMMY("Middleware", 1), NULL("Middleware", 0), LITERAL("Middleware", 1);

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
