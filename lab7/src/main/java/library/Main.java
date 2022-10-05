package library;

public class Main {
    
    public static void main(String[] arcgs) {
    IBook[] books1 = new ChildrenBook[1];
    IBook[] books2 = new ChildrenBook[4];
    IBook[] books3 = new ChildrenBook[1];
    IBook[] books4 = new ChildrenBook[5];

   
    books1[0] = new ChildrenBook("Coleens", "Name2", 3.15, 2008, 8);
    books2[0] = new ChildrenBook("Coles", "Name3", 4.99, 2017, 3);
    books2[1] = new ChildrenBook("Tannet", "Name41", 21.00, 2013, 3);
    books2[2] = new ChildrenBook("Tannet", "Name42", 21.00, 2013, 3);
    books2[3] = new ChildrenBook("Tannet", "Name43", 21.00, 2013, 3);
    books3[0] = new ChildrenBook("Stevens", "Name5", 8.99, 2020, 5);
    books4[0] = new ChildrenBook("Klein", "Name7", 5.75, 2015, 5);
    books4[1] = new ChildrenBook("Author2", "Name8", 21.00, 2013, 3);
    books4[2] = new ChildrenBook("Author3", "Name9", 8.99, 2020, 5);
    books4[3] = new ChildrenBook("Author4", "Name10", 8.99, 2020, 5);
    books4[4] = new ChildrenBook("Author5", "Name11", 5.75, 2015, 5);

    IHall h1 = new ChildrenLibraryHall("Physics", books1);
    IHall h2 = new ChildrenLibraryHall("Math", books2);
    IHall h3 = new ChildrenLibraryHall("Biology", books3);
    IHall h4 = new ChildrenLibraryHall("Chemistry", books4);

    IHall[] hallArr = new ChildrenLibraryHall[4];
    hallArr[0] = h1;
    hallArr[1] = h2;
    hallArr[2] = h3;
    hallArr[3] = h4;
  ILibrary lib = new ChildrenLibrary(hallArr);
  IHall newH = h2.clone();
   System.out.println(h2.toString());
  System.out.println(newH.toString());
  System.out.println(h2.equals(newH));
    }
}
