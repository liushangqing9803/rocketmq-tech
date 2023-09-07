package org.apache.rocketmq.example.quickstart;

import org.apache.commons.cli.*;

/**
 * @author shangqingliu
 * @version v1.0
 * @desc MyStart
 * @date 2020/3/28 3:54 PM
 */
public class MyStart {

    public static void main(String[] args) {
        String[] arg = { "-h","eee", "-c", "con1fig.xml" };
        testOptions(arg);
    }

    public static void testOptions(String[] args) {


        Options options = new Options();
        Option opt = new Option("h", "help", true, "print help");
        opt.setRequired(false);
        options.addOption(opt);


        opt = new Option("c", "configFile", true, "Name server config properties file");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("p", "printConfigItem", false, "Print all config item");
        opt.setRequired(false);
        options.addOption(opt);
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);

        CommandLine commandLine = null;
        CommandLineParser parser = new PosixParser();
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                // 打印使用帮助
                hf.printHelp("testApp", options, true);
            }

            // 打印opts的名称和值
            System.out.println("--------------------------------------");
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt1 : opts) {
                    String name = opt1.getLongOpt();
                    String value = commandLine.getOptionValue(name);
                    System.out.println(name + "=>" + value);
                }
            }
        }
        catch (ParseException e) {
            hf.printHelp("testApp", options, true);
        }

    }
}
