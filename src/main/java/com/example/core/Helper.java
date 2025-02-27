package com.example.core;

import javax.swing.*;

public class Helper {

    public static void setTheme()  {
        for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            if(info.getName().equals("Nimbus")){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static boolean isFieldEmpty(JTextField textField){
        return textField.getText().trim().isEmpty();
    }

    public static void showMsg(String message){
        String msg;
        String title;

        switch (message){
            case "fill":
                msg = "please fill all fields!";
                title = "error";
                break;
            case "done":
                msg = "Action is succesfull";
                title = "done";
                break;
            case "error":
                msg = "an error occured";
                title = "error";
                break;
            default:
                msg = message;
                title = "message";
        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isMailValid(String mail){
        if(mail == null || mail.trim().isEmpty()) return false;

        if (!mail.contains("@")) return false;

        String[] parts = mail.split("@");
        if (parts.length != 2) return false;

        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) return false;

        if (!parts[1].contains(".")) return false;

        return true;

    }

    public static boolean confirm(String str){
        String msg;

        if(str.equals("sure")){
            msg = "Are you sure to do this?";
        }else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"are you sure?",JOptionPane.YES_NO_OPTION) == 0;
    }
}
