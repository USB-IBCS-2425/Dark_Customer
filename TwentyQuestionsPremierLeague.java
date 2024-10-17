import java.util.*;
import java.util.function.Predicate;

class TwentyQuestionsPremierLeague {
    ArrayList<String> possibleAnswers = new ArrayList<>(); 
    ArrayList<String> currentPossibleAnswers; 
    int numQuestions; 

    public TwentyQuestionsPremierLeague() {
    
        possibleAnswers.addAll(Arrays.asList(
            "Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton & Hove Albion", "Burnley", 
            "Chelsea", "Crystal Palace", "Everton", "Fulham", "Liverpool", "Luton Town", 
            "Manchester City", "Manchester United", "Newcastle United", "Nottingham Forest", "Sheffield United", 
            "Tottenham Hotspur", "West Ham United", "Wolverhampton Wanderers"
        ));
    }

    public void printTeamList() {
        System.out.println("Here is the list of Premier League teams to choose from:");
        for (String team : possibleAnswers) {
            System.out.println("- " + team);
        }
    }

    public String askQuestion() {
        if (numQuestions == 0) {
            return "Did the team qualify for the latest UEFA Champions League or Europa League?";
        } else if (numQuestions == 1) {
            return "Has the team ever won the Premier League title?";
        } else if (numQuestions == 2) {
            return "Has the team ever won a major English championship (Premier League or old First Division)?";
        } else if (numQuestions == 3) {
            return "Has the team ever won a European trophy (e.g., UEFA Champions League, Europa League, etc.)?";
        } else if (numQuestions == 4) {
            return "Has the team won the Premier League title in the last 5 years?";
        } else if (numQuestions == 5) {
            return "Did the team qualify for the UEFA Champions League or Europa League last season?";
        } else if (numQuestions == 6) {
            return "Has the team won a domestic cup (FA Cup or League Cup) in the past 10 years?";
        } else if (numQuestions == 7) {
            return "Has the team consistently finished in the top 6 of the Premier League in the past 5 years?";
        } else if (numQuestions == 8) {
            return "Was the team promoted to the Premier League within the last 3 seasons?";
        } else if (numQuestions == 9) {
            return "Is the team located in Manchester?";
        } else if (numQuestions == 10) {
            return "Is the team located in London?";
        } else if (numQuestions == 11) {
            return "Does the team play at Tottenham Hotspur Stadium?";
        } else if (numQuestions == 12) {
            return "Is the team located in Birmingham?";
        } else if (numQuestions == 13) {
            return "Is the team located in Newcastle?";
        } else if (numQuestions == 14) {
            return "Is the team located on the South Coast?";
        } else if (numQuestions == 15) {
            return "Is the team located in Burnley?";
        }
        return "";
    }

    public void updateAnswers(boolean answer, String question) {
        if (question.equals("Did the team qualify for the latest UEFA Champions League or Europa League?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isEuropeanQualifyingTeam(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isEuropeanQualifyingTeam(team);
                    }
                });
            }
        } else if (question.equals("Has the team ever won the Premier League title?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isPremierLeagueWinner(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isPremierLeagueWinner(team);
                    }
                });
            }
        } else if (question.equals("Has the team ever won a major English championship (Premier League or old First Division)?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isHistoricChampionshipWinner(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isHistoricChampionshipWinner(team);
                    }
                });
            }
        } else if (question.equals("Has the team ever won a European trophy (e.g., UEFA Champions League, Europa League, etc.)?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isEuropeanTrophyWinner(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isEuropeanTrophyWinner(team);
                    }
                });
            }
        } else if (question.equals("Has the team won the Premier League title in the last 5 years?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isRecentPremierLeagueWinner(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isRecentPremierLeagueWinner(team);
                    }
                });
            }
        } else if (question.equals("Did the team qualify for the UEFA Champions League or Europa League last season?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isRecentEuropeanQualifier(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isRecentEuropeanQualifier(team);
                    }
                });
            }
        } else if (question.equals("Has the team won a domestic cup (FA Cup or League Cup) in the past 10 years?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isRecentDomesticCupWinner(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isRecentDomesticCupWinner(team);
                    }
                });
            }
        } else if (question.equals("Has the team consistently finished in the top 6 of the Premier League in the past 5 years?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isConsistentTop6(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isConsistentTop6(team);
                    }
                });
            }
        } else if (question.equals("Was the team promoted to the Premier League within the last 3 seasons?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isRecentlyPromoted(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isRecentlyPromoted(team);
                    }
                });
            }
        } else if (question.equals("Is the team located in Manchester?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isManchesterTeam(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isManchesterTeam(team);
                    }
                });
            }
        } else if (question.equals("Is the team located in London?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isLondonTeam(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isLondonTeam(team);
                    }
                });
            }
        } else if (question.equals("Does the team play at Tottenham Hotspur Stadium?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !team.equals("Tottenham Hotspur");
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return team.equals("Tottenham Hotspur");
                    }
                });
            }
        } else if (question.equals("Is the team located in Birmingham?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !team.equals("Aston Villa");
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return team.equals("Aston Villa");
                    }
                });
            }
        } else if (question.equals("Is the team located in Newcastle?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !team.equals("Newcastle United");
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return team.equals("Newcastle United");
                    }
                });
            }
        } else if (question.equals("Is the team located in Burnley?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !team.equals("Burnley");
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return team.equals("Burnley");
                    }
                });
            }
        } else if (question.equals("Is the team located on the South Coast?")) {
            if (answer) {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return !isSouthCoastTeam(team);
                    }
                });
            } else {
                currentPossibleAnswers.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String team) {
                        return isSouthCoastTeam(team);
                    }
                });
            }
        }

        numQuestions++;

        System.out.println("\nRemaining possible teams: " + currentPossibleAnswers + "\n");
    }

    public void guess() {
        if (currentPossibleAnswers.size() == 1) {
            System.out.println("Your team is: " + currentPossibleAnswers.get(0));
        } else if (currentPossibleAnswers.size() > 1) {
            System.out.println("Sorry, I couldn't figure it out. Multiple teams remain.");
        } else {
            System.out.println("Sorry, I couldn't figure it out. No teams match.");
        }
    }

    private boolean isEuropeanQualifyingTeam(String team) {
        return Arrays.asList(
            "Manchester City", "Manchester United", "Arsenal", "Liverpool", 
            "Newcastle United", "Brighton & Hove Albion", "West Ham United"
        ).contains(team);
    }

    private boolean isPremierLeagueWinner(String team) {
        return Arrays.asList(
            "Manchester United", "Manchester City", "Liverpool", "Chelsea", "Arsenal", "Leicester City", "Blackburn Rovers"
        ).contains(team);
    }

    private boolean isHistoricChampionshipWinner(String team) {
        return Arrays.asList(
            "Manchester United", "Liverpool", "Arsenal", "Chelsea", "Manchester City", 
            "Everton", "Aston Villa", "Sunderland"
        ).contains(team);
    }

    private boolean isEuropeanTrophyWinner(String team) {
        return Arrays.asList(
            "Manchester United", "Liverpool", "Chelsea", "Tottenham Hotspur", 
            "Aston Villa", "Nottingham Forest", "Everton"
        ).contains(team);
    }

    private boolean isRecentPremierLeagueWinner(String team) {
        return Arrays.asList(
            "Manchester City", "Liverpool"
        ).contains(team);
    }

    private boolean isRecentEuropeanQualifier(String team) {
        return Arrays.asList(
            "Manchester City", "Manchester United", "Arsenal", "Liverpool", 
            "Newcastle United", "Brighton & Hove Albion"
        ).contains(team);
    }

    private boolean isRecentDomesticCupWinner(String team) {
        return Arrays.asList(
            "Manchester City", "Liverpool", "Arsenal", "Leicester City", "Manchester United"
        ).contains(team);
    }

    private boolean isConsistentTop6(String team) {
        return Arrays.asList(
            "Manchester City", "Manchester United", "Liverpool", "Arsenal", "Chelsea"
        ).contains(team);
    }

    private boolean isRecentlyPromoted(String team) {
        return Arrays.asList(
            "Luton Town", "Sheffield United", "Burnley", "Nottingham Forest"
        ).contains(team);
    }

    private boolean isManchesterTeam(String team) {
        return Arrays.asList("Manchester City", "Manchester United").contains(team);
    }

    private boolean isLondonTeam(String team) {
        return Arrays.asList(
            "Arsenal", "Chelsea", "Crystal Palace", "Fulham", "Tottenham Hotspur", "West Ham United"
        ).contains(team);
    }

    private boolean isSouthCoastTeam(String team) {
        return Arrays.asList("Brighton & Hove Albion", "Bournemouth").contains(team);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the password:");
        String password = scanner.nextLine();
        if (!password.equals("the ohio sigma super rizzler")) {
            System.out.println("Incorrect password! Exiting the game.");
            return;
        }

        TwentyQuestionsPremierLeague game = new TwentyQuestionsPremierLeague();

        boolean playing = true;
        while (playing) {
            game.currentPossibleAnswers = new ArrayList<>(game.possibleAnswers);
            game.numQuestions = 0; 
            game.printTeamList();

            while (game.currentPossibleAnswers.size() > 1) {
                String question = game.askQuestion();
                System.out.println(question);
                String response = scanner.nextLine().trim().toLowerCase();
                boolean answer = response.equals("yes");
                game.updateAnswers(answer, question);
            }

            game.guess();

            System.out.println("Would you like to play again? (yes/no): ");
            String playAgain = scanner.nextLine().trim().toLowerCase();
            if (!playAgain.equals("yes")) {
                playing = false;
                System.out.println("no punches for today:((");
                System.out.println("Watch this: https://www.youtube.com/watch?v=QF3pP4Ye8h8");
            }
        }

        scanner.close();
    }
}
