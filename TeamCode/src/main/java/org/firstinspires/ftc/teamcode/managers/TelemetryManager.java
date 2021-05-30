package org.firstinspires.ftc.teamcode.managers;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.firstinspires.ftc.robotcore.internal.webserver.WebHandler;
import org.firstinspires.ftc.robotcore.internal.webserver.RobotControllerWebInfo;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoTelemetry;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.FunctionStore;
import org.firstinspires.ftc.teamcode.managers.telemetryserver.Server;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.WebServer;
import com.qualcomm.robotcore.util.WebHandlerManager;

public class TelemetryManager extends FeatureManager implements Telemetry {
    public AutoautoTelemetry autoauto;
    private Telemetry backend;
    private LogCatcher backendLog;

    private Gamepad gamepad1;
    private Gamepad gamepad2;

    private Server server;

    private HashMap<String, String> fields= new HashMap<String, String>();;

    private boolean hasNewData;

    public TelemetryManager(Telemetry backend) {
        this.backend = backend;
        this.backendLog = new LogCatcher(backend.log());
        this.server = new Server(this);
        this.autoauto = new AutoautoTelemetry();

        this.fields = new HashMap<String, String>();
    }

    @Override
    public Item addData(String caption, String format, Object... args) {
        if(fields != null) fields.put(caption, String.format(format, args));
        return backend.addData(caption, format, args);
    }

    @Override
    public Item addData(String caption, Object value) {
        if(fields != null) fields.put(caption, value.toString());
        return backend.addData(caption, value);
    }

    @Override
    public <T> Item addData(String caption, Func<T> valueProducer) {
        return backend.addData(caption, valueProducer);
    }

    @Override
    public <T> Item addData(String caption, String format, Func<T> valueProducer) {
        return backend.addData(caption, format, valueProducer);
    }

    @Override
    public boolean removeItem(Item item) {
        return backend.removeItem(item);
    }

    @Override
    public void clear() {
        backend.clear();
    }

    @Override
    public void clearAll() {
        backend.clearAll();
    }

    @Override
    public Object addAction(Runnable action) {
        return backend.addAction(action);
    }

    @Override
    public boolean removeAction(Object token) {
        return backend.removeAction(token);
    }

    @Override
    public void speak(String text) {
        backend.speak(text);
    }

    @Override
    public void speak(String text, String languageCode, String countryCode) {
        backend.speak(text, languageCode, countryCode);
    }

    @Override
    public boolean update() {
        this.hasNewData = true;
        return backend.update();
    }

    @Override
    public Line addLine() {
        return backend.addLine();
    }

    @Override
    public Line addLine(String lineCaption) {
        return backend.addLine(lineCaption);
    }

    @Override
    public boolean removeLine(Line line) {
        return backend.removeLine(line);
    }

    @Override
    public boolean isAutoClear() {
        return backend.isAutoClear();
    }

    @Override
    public void setAutoClear(boolean autoClear) {
        backend.setAutoClear(autoClear);
    }

    @Override
    public int getMsTransmissionInterval() {
        return backend.getMsTransmissionInterval();
    }

    @Override
    public void setMsTransmissionInterval(int msTransmissionInterval) {
        backend.setMsTransmissionInterval(msTransmissionInterval);
    }

    @Override
    public String getItemSeparator() {
        return backend.getItemSeparator();
    }

    @Override
    public void setItemSeparator(String itemSeparator) {
        backend.setItemSeparator(itemSeparator);
    }

    @Override
    public String getCaptionValueSeparator() {
        return backend.getCaptionValueSeparator();
    }

    @Override
    public void setCaptionValueSeparator(String captionValueSeparator) {
        backend.setCaptionValueSeparator(captionValueSeparator);
    }

    @Override
    public void setDisplayFormat(DisplayFormat displayFormat) {
        backend.setDisplayFormat(displayFormat);
    }

    @Override
    public Log log() {
        if(backendLog == null) backendLog = new LogCatcher(backend.log());
        return backendLog;
    }

    public String readData() {
        hasNewData = false;
        String r = "{" +
                "\"time\":" + System.currentTimeMillis();
                r += "," +
                        "\"fields\": { ";
            for(Map.Entry<String, String> field : fields.entrySet()) {
                r += "\"" + field.getKey() + "\":";
                try {
                    //if it's a number, it can just go in as-is
                    float valueAsFloat = Float.parseFloat(field.getValue());
                    r += valueAsFloat;
                } catch(NumberFormatException e) {
                    //if it's not a float, escape it
                    r += "\"" + PaulMath.escapeString(field.getValue()) + "\"";
                }
                r += ",";
            }
            //remove trailing comma. if there were no fields, then it'll remove the extra space inserted for that reason :)
            //hacky? yes. working? also yes
            r = r.substring(0, r.length() - 1);

        r += "}";


        r += ", \"gamepad1Info\": " + getGamepad1Info();
        r += ", \"gamepad2Info\": " + getGamepad2Info();

                r += "}";

        return r;
    }

    public boolean hasNewData() {
        return hasNewData;
    }

    public void setGamepads(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public String getGamepad1Info() {
        if(gamepad1 == null) return "{}";
        else return "{\"left_stick_x\":" + gamepad1.left_stick_x + ", \"left_stick_y\":" + gamepad1.left_stick_y + ", \"right_stick_x\":" + gamepad1.right_stick_x + ", \"right_stick_y\":" + gamepad1.right_stick_y + ", \"dpad_up\":" + gamepad1.dpad_up + ", \"dpad_down\":" + gamepad1.dpad_down + ", \"dpad_left\":" + gamepad1.dpad_left + ", \"dpad_right\":" + gamepad1.dpad_right + ", \"a\":" + gamepad1.a + ", \"b\":" + gamepad1.b + ", \"x\":" + gamepad1.x + ", \"y\":" + gamepad1.y + ", \"guide\":" + gamepad1.guide + ", \"start\":" + gamepad1.start + ", \"back\":" + gamepad1.back + ", \"left_bumper\":" + gamepad1.left_bumper + ", \"right_bumper\":" + gamepad1.right_bumper + ", \"left_stick_button\":" + gamepad1.left_stick_button + ", \"right_stick_button\":" + gamepad1.right_stick_button + ", \"left_trigger\":" + gamepad1.left_trigger + ", \"right_trigger\":" + gamepad1.right_trigger + ", \"circle\":" + gamepad1.circle + ", \"cross\":" + gamepad1.cross + ", \"triangle\":" + gamepad1.triangle + ", \"square\":" + gamepad1.square + ", \"share\":" + gamepad1.share + ", \"options\":" + gamepad1.options + ", \"touchpad\":" + gamepad1.touchpad + ", \"ps\":" + gamepad1.ps + "}";

    }
    public String getGamepad2Info() {
        if(gamepad2 == null) return "{}";
        else return "{\"left_stick_x\":" + gamepad2.left_stick_x + ", \"left_stick_y\":" + gamepad2.left_stick_y + ", \"right_stick_x\":" + gamepad2.right_stick_x + ", \"right_stick_y\":" + gamepad2.right_stick_y + ", \"dpad_up\":" + gamepad2.dpad_up + ", \"dpad_down\":" + gamepad2.dpad_down + ", \"dpad_left\":" + gamepad2.dpad_left + ", \"dpad_right\":" + gamepad2.dpad_right + ", \"a\":" + gamepad2.a + ", \"b\":" + gamepad2.b + ", \"x\":" + gamepad2.x + ", \"y\":" + gamepad2.y + ", \"guide\":" + gamepad2.guide + ", \"start\":" + gamepad2.start + ", \"back\":" + gamepad2.back + ", \"left_bumper\":" + gamepad2.left_bumper + ", \"right_bumper\":" + gamepad2.right_bumper + ", \"left_stick_button\":" + gamepad2.left_stick_button + ", \"right_stick_button\":" + gamepad2.right_stick_button + ", \"left_trigger\":" + gamepad2.left_trigger + ", \"right_trigger\":" + gamepad2.right_trigger + ", \"circle\":" + gamepad2.circle + ", \"cross\":" + gamepad2.cross + ", \"triangle\":" + gamepad2.triangle + ", \"square\":" + gamepad2.square + ", \"share\":" + gamepad2.share + ", \"options\":" + gamepad2.options + ", \"touchpad\":" + gamepad2.touchpad + ", \"ps\":" + gamepad2.ps + "}";
    }

    private static class LogCatcher implements Log {
        private Log backend;

        private String logged;

        public LogCatcher(Log backend) {
            this.backend = backend;
            this.logged = "";
        }

        public String readLog() {
            String log = logged;
            this.logged = "";
            return log;
        }

        @Override
        public int getCapacity() {
            return backend.getCapacity();
        }

        @Override
        public void setCapacity(int capacity) {
            backend.setCapacity(capacity);
        }

        @Override
        public DisplayOrder getDisplayOrder() {
            return backend.getDisplayOrder();
        }

        @Override
        public void setDisplayOrder(DisplayOrder displayOrder) {
            backend.setDisplayOrder(displayOrder);
        }

        @Override
        public void add(String entry) {
            logged += entry + "\n";
            backend.add(entry);
        }

        @Override
        public void add(String format, Object... args) {
            logged += String.format(format, args) + "\n";
            backend.add(format, args);
        }

        @Override
        public void clear() {
            logged = "";
            backend.clear();
        }
    }
}
