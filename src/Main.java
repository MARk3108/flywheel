import java.awt.*;
import java.io.File;
import javax.sound.sampled.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, AWTException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Введите 1-секудндомер"+"\n"+"Введите 2-таймер"+"\n"+"Введите 3-будильник"+"\n"+"Введите 0-выход");
        int choice=scanner.nextInt();
        while (choice!=0){
            if(choice==1){
                second();
            }
            if(choice==2){
                timer();
            }
            if(choice==3){
                alarm();
            }
            System.out.println("Введите 1-секудндомер"+"\n"+"Введите 2-таймер"+"\n"+"Введите 3-будильник"+"\n"+"Введите 0-выход");
            choice=scanner.nextInt();
            if(choice==0){
                System.exit(0);
            }
        }
    }
    
    
    public static void second(){
        int sec=0;
        int min=0;
        int hour=0;
        while(true){
            if(sec==60){
                sec=0;
                min++;
            }
            if(min==60){
                min=0;
                hour++;
            }
            String secund=Integer.toString(sec);
            String minut=Integer.toString(min);
            String chas=Integer.toString(hour);
            if(sec<=9){
                secund=0+secund;
            }
            if(min<=9){
                minut=0+minut;
            }
            if(hour<=9){
                chas=0+chas;
            }
            System.out.println(chas+":"+minut+":"+secund);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sec++;
        }
    }
    




    public static void timer() throws IOException, UnsupportedAudioFileException, LineUnavailableException, AWTException {
        System.out.println("Сколько засечь минут?");
        int interv_min=new Scanner(System.in).nextInt();
        System.out.println("Сколько засечь секунд?");
        int interv_sec=new Scanner(System.in).nextInt();
        int end_time=interv_min*60+interv_sec;
        int sec_left;
        int min_left;
        for (int i =end_time; i!=0 ; i--) {
            min_left=i/60;
            sec_left=i-(i/60)*60;
            String second_left=Integer.toString(sec_left);
            String minut_left=Integer.toString(min_left);
            if(sec_left<=9){
                second_left=0+second_left;
            }
            if(min_left<=9){
                minut_left=0+minut_left;
            }
            System.out.println(minut_left+":"+second_left);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("images/tray.gif");
            TrayIcon trayIcon = new TrayIcon(image);
            tray.add(trayIcon);
            trayIcon.displayMessage("Внимание", "Время вышло",
                    TrayIcon.MessageType.INFO);
        }
        File file=new File("icq.wav");
        AudioInputStream audioStream= AudioSystem.getAudioInputStream(file);
        Clip clip=AudioSystem.getClip();
        clip.open(audioStream);
        clip.setFramePosition(0);
        clip.start();
    }




    public static void alarm() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("Введите время в формате hh:mm:ss");
        String time_awoke=new Scanner(System.in).nextLine();
        System.out.println("Сколько осилишь примеров?");
        int guess=new Scanner(System.in).nextInt();
        String curr_time=new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        while(!(time_awoke.equals(curr_time))){
            curr_time=new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        }
        int counter=0;
        while (true) {
            Scanner alarmscan=new Scanner(System.in);
            File file=new File("bell.wav");
            AudioInputStream audioStream= AudioSystem.getAudioInputStream(file);
            Clip clip=AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            while(counter!=guess) {
                int a = (int) (Math.random() * 1000);
                int b = (int) (Math.random() * 1000);
                System.out.print(a+"+"+b+"=");
                int check=alarmscan.nextInt();
                if(check==a+b){
                    counter++;
                }
            }
            clip.stop();
            clip.close();
            break;
        }
    }
}
