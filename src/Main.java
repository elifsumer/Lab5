import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AssistantManager manager = new AssistantManager();

        HomeAssistant homeAssistant = new HomeAssistant("Home Assistant", 1.0);
        PersonalFinanceAssistant financeAssistant = new PersonalFinanceAssistant("Finance Assistant", 1.0);
        LanguageTranslatorAssistant translatorAssistant = new LanguageTranslatorAssistant("Translator Assistant", 1.0);

        manager.addAssistant(homeAssistant);
        manager.addAssistant(financeAssistant);
        manager.addAssistant(translatorAssistant);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nWhich assistant would you like to interact with?");
            System.out.println("1. Home Assistant");
            System.out.println("2. Finance Assistant");
            System.out.println("3. Translator Assistant");
            System.out.println("Type 'exit' to quit.");

            String choice = sc.nextLine().trim();
            if (choice.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the application... Goodbye!");
                break;
            }

            VirtualAssistant selectedAssistant = null;

            switch (choice) {
                case "1":
                    selectedAssistant = homeAssistant;
                    break;
                case "2":
                    selectedAssistant = financeAssistant;
                    break;
                case "3":
                    selectedAssistant = translatorAssistant;
                    break;
                case "4":
                    System.out.println("Enter a general command for all assistants:");
                    String task = sc.nextLine().trim();
                    manager.interactWithAll(task);
                    continue;
                default:
                    System.out.println("Invalid choice! Please select a valid assistant.");
                    continue;
            }

            System.out.println("\nYou selected: " + selectedAssistant.getAssistantName());

            while (true) {
                switch (choice) {
                    case "1":
                        System.out.println("What would you like to do? (Home Assistant)");
                        System.out.println("1. Turn on lights");
                        System.out.println("2. Turn off lights");
                        break;
                    case "2":
                        System.out.println("What would you like to do? (Finance Assistant)");
                        System.out.println("1. Show balance");
                        System.out.println("2. Deposit money");
                        System.out.println("3. Withdraw money");
                        break;
                    case "3":
                        System.out.println("What would you like to do? (Translator Assistant)");
                        System.out.println("1. Translate 'hello' to Spanish");
                        System.out.println("2. Translate 'thank you' to French");
                        break;
                }
                System.out.println("Type 'back' to return to the main menu or 'exit' to quit.");

                String taskChoice = sc.nextLine().trim();
                if (taskChoice.equals("exit")) {
                    System.out.println("Exiting the application... Goodbye!");
                    return;
                }
                if (taskChoice.equals("back")) {
                    break;
                }

                String task = "";

                switch (choice) {
                    case "1":
                        switch (taskChoice) {
                            case "1":
                                task = "turn on lights";
                                break;
                            case "2":
                                task = "turn off lights";
                                break;
                            default:
                                System.out.println("Invalid choice for Home Assistant!");
                                continue;
                        }
                        break;
                    case "2":
                        switch (taskChoice) {
                            case "1":
                                task = "show balance";
                                break;
                            case "2":
                                System.out.println("Enter the amount to deposit:");
                                String depositAmount = sc.nextLine().trim();
                                task = "deposit money " + depositAmount;
                                break;
                            case "3":
                                System.out.println("Enter the amount to withdraw:");
                                String withdrawAmount = sc.nextLine().trim();
                                task = "withdraw " + withdrawAmount;
                                break;
                            default:
                                System.out.println("Invalid choice for Finance Assistant!");
                                continue;
                        }
                        break;
                    case "3":
                        switch (taskChoice) {
                            case "1":
                                task = "translate hello to Spanish";
                                break;
                            case "2":
                                task = "translate thank you to French";
                                break;
                            default:
                                System.out.println("Invalid choice for Translator Assistant!");
                                continue;
                        }
                        break;
                }

                System.out.println(selectedAssistant.greetUser());
                System.out.println(selectedAssistant.performTask(task));
            }
        }
        sc.close();
    }
}


abstract class VirtualAssistant {
    private String assistantName;
    private double version;

    public VirtualAssistant(String assistantName, double version) {
        this.assistantName = assistantName;
        this.version = version;
    }

    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public abstract String greetUser();

    public abstract String performTask(String task);
}

class HomeAssistant extends VirtualAssistant {
    private boolean isLightOn;

    public HomeAssistant(String assistantName, double version) {
        super(assistantName, version);
        this.isLightOn = false;
    }

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
    }

    @Override
    public String greetUser() {
        return "Hello! I’m your Home Assistant. How can I help to control your home today?";
    }

    @Override
    public String performTask(String task) {
        if (task.equals("turn on lights")) {
            if (isLightOn) {
                return "The lights are already turned on.";
            } else {
                isLightOn = true;
                return "Turning on the lights!";
            }
        } else if (task.equals("turn off lights")) {
            if (!isLightOn) {
                return "The lights are already turned off.";
            } else {
                isLightOn = false;
                return "Turning off the lights!";
            }
        } else {
            return "Sorry, I can't do that.";
        }

    }
}

class PersonalFinanceAssistant extends VirtualAssistant {
    private double currentBalance;

    public PersonalFinanceAssistant(String assistantName, double version) {
        super(assistantName, version);
        this.currentBalance = 500.0;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public String greetUser() {
        return "Hi! I’m your Finance Assistant. Let’s manage your money wisely!";

    }

    @Override
    public String performTask(String task) {
        if (task.equals("show balance")) {
            return "Your current balance: " + currentBalance + " dollars.";
        } else if (task.startsWith("deposit money")) {
            double amount = Double.parseDouble(task.split(" ")[2]);
            currentBalance += amount;
            return amount + " dollars is deposited into your account. Your current balance: " + currentBalance + " dollars.";
        } else if (task.startsWith("withdraw")) {
            double amount = Double.parseDouble(task.split(" ")[1]);
            if (currentBalance < amount) {
                return "Sorry, insufficient balance!";
            } else {
                currentBalance -= amount;
                return amount + " dollars is withdrawn from your account. Your current balance: " + currentBalance + " dollars.";
            }
        } else {
            return "I don’t know how to do that.";
        }

    }

}

class LanguageTranslatorAssistant extends VirtualAssistant {
    private String lastTranslatedWord;

    public LanguageTranslatorAssistant(String assistantName, double version) {
        super(assistantName, version);
        this.lastTranslatedWord = "None";
    }

    public String getLastTranslatedWord() {
        return lastTranslatedWord;
    }

    public void setLastTranslatedWord(String lastTranslatedWord) {
        this.lastTranslatedWord = lastTranslatedWord;
    }

    @Override
    public String greetUser() {
        return "Bonjour! Hola! Hello! I’m your Language Translator AI!";

    }

    @Override
    public String performTask(String task) {
        if ("translate hello to Spanish".equals(task)) {
            lastTranslatedWord = "Hola";
            return "Hello in Spanish is Hola.";
        } else if ("translate thank you to French".equals(task)) {
            lastTranslatedWord = "Merci";
            return "Thank you in French is Merci.";
        } else {
            return "I don’t know that language yet.";
        }
    }
}

class AssistantManager {
    private List<VirtualAssistant> assistants;

    public AssistantManager() {
        this.assistants = new ArrayList<>();
    }

    public List<VirtualAssistant> getAssistants() {
        return assistants;
    }

    public void setAssistants(List<VirtualAssistant> assistants) {
        this.assistants = assistants;
    }

    public void addAssistant(VirtualAssistant assistant) {
        assistants.add(assistant);
    }

    public void removeAssistant(VirtualAssistant assistant) {
        assistants.remove(assistant);
    }

    public List<String> interactWithAll(String task) {
        List<String> responses = new ArrayList<>();

        for (VirtualAssistant assistant : assistants) {
            String greeting = assistant.greetUser();
            responses.add(greeting);
            System.out.println(greeting);

            String taskResult = assistant.performTask(task);
            responses.add(taskResult);
            System.out.println(taskResult);
        }
        return responses;
    }
}

