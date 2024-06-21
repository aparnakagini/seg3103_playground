import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.easymock.EasyMock.*;
import org.junit.jupiter.api.Test;

class TwitterTest {

    @Test
    void isMentioned_lookForAtSymbol() {
        Twitter twitter = partialMockBuilder(Twitter.class)
            .addMockedMethod("loadTweet")
            .createMock();

        expect(twitter.loadTweet()).andReturn("I am tweet that likes to talk about @me").times(2);
        replay(twitter);

        boolean actual = twitter.isMentioned("me");
        assertEquals(true, actual);

        actual = twitter.isMentioned("you");
        assertEquals(false, actual);

        verify(twitter);
    }

    @Test
    void isMentioned_dontReturnSubstringMatches() {
        Twitter twitter = partialMockBuilder(Twitter.class)
            .addMockedMethod("loadTweet")
            .createMock();

        expect(twitter.loadTweet()).andReturn("hello @meat").times(2);
        replay(twitter);

        boolean actual = twitter.isMentioned("me");
        assertEquals(false, actual);

        actual = twitter.isMentioned("meat");
        assertEquals(true, actual);

        verify(twitter);
    }

    @Test
    void isMentioned_superStringNotFound() {
        Twitter twitter = partialMockBuilder(Twitter.class)
            .addMockedMethod("loadTweet")
            .createMock();

        expect(twitter.loadTweet()).andReturn("I am tweet that likes to talk about @me").times(2);
        replay(twitter);

        boolean actual = twitter.isMentioned("me");
        assertEquals(true, actual);

        actual = twitter.isMentioned("meat");
        assertEquals(false, actual);

        verify(twitter);
    }

    @Test
    void isMentioned_handleNull() {
        Twitter twitter = partialMockBuilder(Twitter.class)
            .addMockedMethod("loadTweet")
            .createMock();

        expect(twitter.loadTweet()).andReturn(null).times(2);
        replay(twitter);

        boolean actual = twitter.isMentioned("me");
        assertEquals(false, actual);

        actual = twitter.isMentioned("meat");
        assertEquals(false, actual);

        verify(twitter);
    }
}