import java.util.Base64;

public class App {
    public static void main(String[] args) throws Exception {
        final var text = "hello world";
        System.out.println("text: " + text);

        final var encryptedText = CryptoAES.encrypt(text.getBytes());
        System.out.println("ecrypted: " + Base64.getEncoder().encodeToString(encryptedText));

        final var decryptedText = CryptoAES.decrypt(encryptedText);
        System.out.println("decrypted: " + new String(decryptedText));
    }
}
