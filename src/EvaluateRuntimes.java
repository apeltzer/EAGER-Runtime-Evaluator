import java.io.*;

/**
 * Created by peltzer on 03.07.17.
 */
public class EvaluateRuntimes {
    private FileReader fr;
    private BufferedReader bfr;
    private BufferedWriter bfw;
    private FileWriter fw;

    public EvaluateRuntimes(String[] args) throws IOException {
        fr = new FileReader(new File(args[0]));
        fw = new FileWriter(new File(args[0] + "_counted.csv"));
        bfw = new BufferedWriter(fw);
        bfr = new BufferedReader(fr);

        String currline = "";
        String module_name = "";
        String module_runtime = "";
        bfw.write("Module name" + "\t" + "Module Runtime" + "\n");
        while ((currline = bfr.readLine()) != null) {
            if (currline.contains("commandline")) { //Then currline contains modulename
                String[] currSplit = currline.split(" ");
                module_name = currSplit[0].substring(1, currSplit[0].length()); //get rid of # in the beginning
            } else { //We have a runtime info
                String[] currSplit = currline.split(":");
                String timeInfo = currSplit[1];
                //We should compile hours and minutes to seconds again here
                int counter = 0;
                if (timeInfo.contains("hours")) {
                    String[] split = timeInfo.split("hour");
                    int hours = Integer.parseInt(split[0].trim());
                    counter += hours * 60 * 60;
                }
                if (timeInfo.contains("minutes")) {
                    String[] split = timeInfo.split("minutes");
                    int minutes = Integer.parseInt(split[0].trim());
                    counter += minutes * 60;
                }
                if (timeInfo.contains("seconds")) {
                    String[] split = timeInfo.split("seconds.");
                    int seconds;
                    if(timeInfo.contains("minutes") | (timeInfo.contains("hours"))){
                        String[] split_and = split[0].split("and");
                         seconds = Integer.parseInt(split_and[1].trim());
                    } else {
                         seconds = Integer.parseInt(split[0].trim());
                    }

                    counter += seconds;
                }
                bfw.write(module_name + "\t" + counter + "\n");
                bfw.flush();
            }
        }

        bfw.flush();
        bfw.close();
    }


    public static void main(String[] args) throws IOException {
        EvaluateRuntimes evr = new EvaluateRuntimes(args);

    }
}
