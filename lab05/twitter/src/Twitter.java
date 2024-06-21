public class Twitter {

  public String loadTweet() {
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {}

    double r = Math.random();
    if (r <= 0.45) {
      return "I am tweet that likes to talk about @me";
    } else if (r <= 0.9) {
      return "Hello to @you";
    } else {
      return null;
    }
  }

  public boolean isMentioned(String name) {
    String tweet = loadTweet();
    if (tweet == null || name == null) {
      return false;
    }
    // Ensure that the @ symbol precedes the username and it's not part of another word
    String mention = "@" + name;
    for (String word : tweet.split(" ")) {
      if (word.equals(mention)) {
        return true;
      }
    }
    return false;
  }
}
