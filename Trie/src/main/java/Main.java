/**
 * Created by user on 23.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();
        System.out.println("Hello world");
        trie.add("Aba");
        trie.add("Abd");
        trie.remove("Aba");
        System.out.println(trie.contains("Aba"));
        System.out.println(trie.contains("Abd"));
        trie.add("Aba");
        System.out.println(trie.howManyStartsWithPrefix("Aba"));
        /*trie.add("Abc");

        trie.add("Dbd");
        trie.add("Atd");
        System.out.println(trie.contains("Aba"));
        System.out.println(trie.contains("Abc"));
        System.out.println(trie.contains("Abd"));
        System.out.println(trie.contains("Dbd"));
        System.out.println(trie.contains("Atd"));
        System.out.println(trie.contains("Atk"));
        System.out.println(trie.contains("Dtt"));
        System.out.print(trie.howManyStartsWithPrefix("Db"));*/
    }
}
