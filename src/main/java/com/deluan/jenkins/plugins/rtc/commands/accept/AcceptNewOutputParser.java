package com.deluan.jenkins.plugins.rtc.commands.accept;

/**
 * @author deluan
 */
public class AcceptNewOutputParser extends BaseAcceptOutputParser {

    public AcceptNewOutputParser() {
        super("^\\s{8}[^\\d\\s]+(\\d+)[^\\d]+\\s(.*)$",
                "^\\s{12}(.{5})\\s+(.*)$",
                "^\\s{12}[^\\d\\s]+(\\d+)[^\\d]+(.*)$");
    }

    @Override
    protected String parseWorkItem(String string) {
        return string;
    }

    @Override
    protected String parseEditFlag(String string) {
        return string.substring(2, 3);
    }
}
