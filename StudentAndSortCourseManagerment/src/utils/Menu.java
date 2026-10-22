package utils;

import java.util.ArrayList;

public class Menu<T> {
    private Inputter inputter;
    private String title;
    private ArrayList<String> optionList = new ArrayList<>();

    public Menu(String title, Inputter inputter) {
        this.title = title;
        this.inputter = inputter;
    }

    public void addNewOption(String newOption) {
        optionList.add(newOption);
    }

    public void show() {
        int seq = 1;
        System.out.println("+++++ " + title + " +++++");
        for (String string : optionList) {
            System.out.println(seq + ". " + string);
            seq++;
        }
    }

    public int getChoice() {
        return inputter.getAnInteger("Enter your choice: ",
                "Choice must be from 1 to " + optionList.size() + "!\n",
                1, optionList.size());
    }

    public T ref_getChoice(ArrayList<T> list) {
        int seq = 1;
        for (T itemT : list) {
            System.out.println(seq + ". " + itemT);
            seq++;
        }
        int choice = inputter.getAnInteger("Enter your choice: ",
                "Choice must be from 1 to " + list.size() + "!\n",
                1, list.size());
        return list.get(choice - 1);
    }
}
