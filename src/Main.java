import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static List<Thread> mThreadList;

    public static void main(String[] args) throws IOException, InterruptedException {
        mThreadList = new ArrayList<Thread>();
        try {
            ////
            InputStream is = new BufferedInputStream(new FileInputStream("WO"));
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            ////
            int uidSum = ++count;

            FileInputStream fstream = new FileInputStream("WO");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            for (int g = 0; g < uidSum; g++) {
                while ((strLine = br.readLine()) != null) {
                    String delimeter = " ";
                    String[] res = strLine.split(delimeter);
                    String uid = res[0];
                    String ids = res[1];
                    String mailTo = res[2];

                    mThreadList.add(new Thread() {
                        @Override
                        public void run() {
                            int time = 0;
                            try {
                                FileInputStream fileInputStream = new FileInputStream("time");
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                                String Line;
                                while ((Line = bufferedReader.readLine()) != null) {
//                                    System.out.println("Check in: " + Line + " min.");
                                    time = Integer.parseInt(Line);
                                }
                                for (; ; Thread.sleep((1000 * 60) * time)) {
                                    System.out.println(uid);

                                    try {
                                        SendHttpPost req = new SendHttpPost();
                                        int code = req.CheckWO(uid);
                                        if (code != 200) {
//                                            System.out.println(uid + " <<< err " + new Date());
                                            Thread.sleep(1);
                                            int secondCode = req.CheckWO(uid);
                                            if (secondCode != 200) {
//                                                System.out.println(uid + " <<< err(2) " + new Date());
                                                Thread.sleep( 1);
                                                int thirdCode = req.CheckWO(uid);
                                                if (thirdCode != 200) {
//                                                    System.out.println(uid + " <<< Error| |code = " + thirdCode + "| |" + new Date() + "| |" + getName());
                                                    SendMail mail = new SendMail();
                                                    mail.Send(ids, mailTo);
//                                                    System.out.println(mailTo + "<<<письмо>>> " + new Date());
//                                                    currentThread().join();
                                                } else {
//                                                    System.out.println(uid + " <<< OK " + new Date() + "| |" + getName());
                                                }
                                            } else {
//                                                System.out.println(uid + " <<< OK " + new Date() + "| |" + getName());
                                            }
                                        } else {
//                                            System.out.println(uid + " <<< OK " + new Date() + "| |" + getName());
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e + "<<<<75");
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("<<<<50");
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
            System.out.println("© Made by Roman Bozhenko");

            for (Thread currentThread : mThreadList) {
                currentThread.start();
            }
        } catch (Exception e) {
            System.out.println(e + "<<<<96");
        }
    }
}