import javax.swing.*;

import java.awt.Color;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.lang.Math;

public class ChatBot extends JFrame implements KeyListener {

    JPanel panel = new JPanel();
    JTextArea dialog = new JTextArea(40,85);
    JTextArea input = new JTextArea(3,85);
    JScrollPane scroll = new JScrollPane(
            dialog,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    String[][] chatBot = {
            //standard greetings
            {"hi", "hello", "hola", "ola", "howdy", "hey"},
            {"hi", "hello", "hey"},
            //question greetings
            {"how are you", "how r you", "how r u", "how are u"},
            {"good", "doing well"},
            //yes
            {"yes"},
            {"no", "NO", "NO!!!!!!!"},
            //default
            {"I could not understand you.", "Can you repeat, please?"}
    };

    public ChatBot(){
        super("Chat Bot");
        setSize(1024,760);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        dialog.setEditable(false);
        input.addKeyListener(this);

        panel.add(scroll);
        panel.add(input);
        panel.setBackground(new Color(255,200,0));
        add(panel);

        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(false);
            //-----grab quote-----------
            String quote = input.getText();
            if (quote.isEmpty()) {
                quote = "Not a valid text!";
            }
            addText("-->You:\t" + quote);
            input.setText("");
            quote.trim();
            while(
                            quote.charAt(quote.length()-1)=='!' ||
                            quote.charAt(quote.length()-1)=='.' ||
                            quote.charAt(quote.length()-1)=='?'
            ){
                quote = quote.substring(0, quote.length() - 1);
            }
            quote.trim();
            byte response = 0;
			/*
			0:we're searching through chatBot[][] for matches
			1:we didn't find anything
			2:we did find something
			*/
            //-----check for matches----
            int j = 0;//which group we're checking
            while(response == 0) {
                if(inArray(quote.toLowerCase(), chatBot[j * 2])){
                    response = 2;
                    int randomNum = (int)Math.floor(Math.random() * chatBot[(j * 2) + 1].length);
                    addText("\n-->Michael\t" + chatBot[(j * 2) + 1][randomNum]);
                }
                j++;

                if (j * 2 == chatBot.length - 1 && response==0){
                    response = 1;
                }
            }

            //-----default--------------
            if (response == 1){
                int randomNum = (int) Math.floor(Math.random() * chatBot[chatBot.length - 1].length);
                addText("\n-->Michael\t" + chatBot[chatBot.length - 1][randomNum]);
            }
            addText("\n");
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            input.setEditable(true);
        }
    }

    public void keyTyped(KeyEvent e){

    }

    public void addText(String str) {
        dialog.setText(dialog.getText() + str);
    }

    public boolean inArray(String in, String[] str){
        boolean match = false;
        for(int i = 0; i < str.length; i++){
            if (str[i].equals(in)) {
                match=true;
            }
        }
        return match;
    }

    public static void main(String[] args) {
        new ChatBot();
    }
}