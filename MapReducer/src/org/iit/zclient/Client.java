package org.iit.zclient;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.iit.zdoop.*;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		Options options = new Options();
		options.addOption("f", "file", true, "path to data file");
		options.addOption("h", "help", false, "print this message");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("h")) {
				printHelp(options);
				return;
			}

			Thread.sleep(5000);
			// Initialize hadoop
			ZHadoop hadoop = new ZHadoop();

			// Simulate read configuration file.
			Config cfg = new Config();
			byte[] data = "Hello world hello world\n hi world hello you\n nice to meet you\n Hello welcome to chicago"
					.getBytes();

			if (cmd.hasOption("f")) {
				data = Util.readFile(cmd.getOptionValue("f"));
			}
			hadoop.start(cfg, args);

			// Start a job with mapper and reducer, also, we need specify the
			// input(data) and output(result)
			if (!hadoop.isCmdMode()) {
				Job job = new Job();
				job.setMapper(MyMapper.class);
				job.setReducer(MyReducer.class);

				job.setData(data);

				// Start hadoop
				hadoop.execute(job);
			} else {
				hadoop.startcmd();
			}
		} catch (ParseException e) {
			System.err.println("illegal option");
			printHelp(options);
			System.exit(1);
		}
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("client", options, true);
	}
}
