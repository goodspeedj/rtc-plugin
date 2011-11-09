package com.deluan.jenkins.plugins.rtc.changelog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @author deluan
 */
public class JazzChangeLogWriter {

    public void write(Collection<JazzChangeSet> changeSetList, File changelogFile) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(changelogFile));
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<changelog>");

        writeChangeSetList(changeSetList, writer);

        writer.println("</changelog>");
        writer.close();
    }

    private void writeChangeSetList(Collection<JazzChangeSet> changeSetList, PrintWriter writer) {
        for (JazzChangeSet changeSet : changeSetList) {
            writeChangeSet(changeSet, writer);
        }
    }

    private void writeChangeSet(JazzChangeSet changeSet, PrintWriter writer) {
        writer.println(String.format("\t<changeset rev=\"%s\">", changeSet.getRev()));
        writer.println(String.format("\t\t<date>%s</date>", changeSet.getDateStr()));
        writer.println(String.format("\t\t<user>%s</user>", escapeForXml(changeSet.getUser())));
        writer.println(String.format("\t\t<email>%s</email>", escapeForXml(changeSet.getEmail())));
        writer.println(String.format("\t\t<comment>%s</comment>", escapeForXml(changeSet.getMsg())));

        if (!changeSet.hasItems()) {
            writeItems(changeSet, writer);
        }

        if (!changeSet.hasWorkItems()) {
            writeWorkItems(changeSet, writer);
        }
        writer.println("\t</changeset>");
    }

    private void writeWorkItems(JazzChangeSet changeSet, PrintWriter writer) {
        writer.println("\t\t<workitems>");
        for (String workItem : changeSet.getWorkItems()) {
            writer.println(String.format("\t\t\t<workitem>%s</workitem>", escapeForXml(workItem)));
        }
        writer.println("\t\t</workitems>");
    }

    private void writeItems(JazzChangeSet changeSet, PrintWriter writer) {
        writer.println("\t\t<files>");
        for (JazzChangeSet.Item item : changeSet.getItems()) {
            writer.println(String.format("\t\t\t<file action=\"%s\">%s</file>", item.getAction(),
                    escapeForXml(item.getPath())));
        }
        writer.println("\t\t</files>");
    }

    /**
     * Converts the input in the way that it can be written to the XML.
     * Special characters are converted to XML understandable way.
     *
     * @param object The object to be escaped.
     * @return Escaped string that can be written to XML.
     */
    private String escapeForXml(Object object) {
        if (object == null) {
            return null;
        }

        //Loop through and replace the special chars.
        String string = object.toString();
        int size = string.length();
        char ch;
        StringBuilder escapedString = new StringBuilder(size);
        for (int index = 0; index < size; index++) {
            //Convert special chars.
            ch = string.charAt(index);
            switch (ch) {
                case '&':
                    escapedString.append("&amp;");
                    break;
                case '<':
                    escapedString.append("&lt;");
                    break;
                case '>':
                    escapedString.append("&gt;");
                    break;
                case '\'':
                    escapedString.append("&apos;");
                    break;
                case '\"':
                    escapedString.append("&quot;");
                    break;
                default:
                    escapedString.append(ch);
            }
        }

        return escapedString.toString();
    }


}
