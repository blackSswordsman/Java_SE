package library;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.io.Serializable;
import java.util.Objects;

public  class ScientificLibrary implements ILibrary, Serializable {
    
    class Obj {
        public IHall data;
        public Obj prev;
        public Obj next;

        public Obj (IHall _hall, Obj _next, Obj _prev) {
            data = _hall;
            next = _next;
            prev=_prev;
        }

        public Obj(IHall _hall) {
            data = _hall;
        }
    }
    
    private int size=0;
    Obj first = null;
    int hallsCnt;
    int [] bookCnt;

    public void setSize(int size) {
        this.size = size;
    }
    
    public int getSize() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int hallsCount() {
        return hallsCnt;
    }

    public void setHallsCnt(int hallsCnt) {
        this.hallsCnt = hallsCnt;
    }
//конструкторы 
    
    public ScientificLibrary (int hallsCnt, int [] bookCnt) {
        this.hallsCnt=hallsCnt;
             for (int j=0;j < hallsCnt; j++){
               ScientificLibraryHall h = new ScientificLibraryHall ();
               h.setSize(bookCnt[j]); 
               if (bookCnt[j]<0){
            throw new InvalidBookCountException("Amount of Books is a negative number!");
        }
               addElement(h);
             }   
}

       public ScientificLibrary(ScientificLibraryHall [] halls) {
        for (ScientificLibraryHall h : halls) {
            addElement(h);
        }
    }
    
public final void addElement(IHall _hall) {//добавление элемента библ
        if (isEmpty()) {
            first = new Obj(_hall);
            first.next = first;
            first.prev=first;
        }
        Obj last = first.prev;
        last.next = new Obj(_hall);
        last.next.next=first;
        last.next.prev=last;
        first.prev=last.next;
        ++size;
}
       
    @Override
     public int booksCount(int index){ //кол-во книг в зале
        return this.getHall(index).getSize();
      }
       
    @Override
       public int sumAllBooks(){ //всего книг в библиоеке
           int sum=0;
           for (int j=0;j < hallsCnt; j++){
              sum+= bookCnt[j];
           }
           return sum;
       }
       
    @Override
       public IHall [] hallsArr (){ //массив залов 
           if (isEmpty()){
               return null;
           }
           Obj last = first;
          IHall [] showArr = new IHall [hallsCnt];
           do {
               for (int i=0; i<hallsCnt; i++){
                   showArr[i]=last.data;
               }
           }while (first.prev!=last || last.next!=first);
           return showArr;
       }
       
    @Override
       public IHall getHall (int hallNumber){//получеие зала по номеру
           if (isEmpty()){
               return null;
           }
           Obj last = first;
            int cnt = 0;
           if (hallNumber > hallsCnt){
               throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
           } else{
        do {
            ++cnt;
            last = last.next;
        } while (last != first && cnt + 1 != hallNumber);
           } 
        return last.data;
       }
       
    @Override
       public IBook getBook (int num){//получение книги по номеру в библ
           int cnt=0;
           Obj p=first;
           do {
             if (num<cnt+p.data.getSize()){
                return p.data.getBook(num-cnt);
           } 
           else {
               p=p.next;
           cnt+=p.data.getSize();
           if (cnt>this.hallsCount()){
               throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
           }
           } 
           }while (p!=first);
           return null;
       }
       
    @Override
       public void printHalls(){ //вывод залов
           Obj p =first;  
           do{
             System.out.println(p.data.getHallName() + "  " + p.data.getSize());
             p=p.next;
           }while(p!=first);
       }
       
    @Override
       public void replaceHall (int hallNum, IHall h ){//замеа зала
           if (hallNum>this.hallsCount()){
               throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
           }
           Obj p =first;
           Obj _new = new Obj (h);
           for (int cnt=0;cnt<hallNum;cnt++){
               p=p.next;
           }
           Obj sled = p.next;
           Obj pred=p.prev;
           pred.next=_new;
           sled.prev=_new;
           _new.prev=pred;
           _new.next=sled;
       }
       
    @Override
       public void replaceBook (int bookInd, IBook book  ){//замена книги
         if (bookInd <sumAllBooks()){
           int cnt=0;
           Obj p=first;
           do {
             if (bookInd<cnt+p.data.getSize()){
             p.data.replaceBook(bookInd, book);
             } else{
                 p=p.next;
                 cnt+=p.data.getSize();
                 if (cnt>this.hallsCount()){
                     throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
                 }
             }
           }while(p!=first);
         }
        }
       
    @Override
       public IBook[] srtBooks (){//сортировка 
           int _size = sumAllBooks();
           IBook [] allBooksArr = new IBook [_size];
           int cnt=0;
           for (int i=0;i<getSize();i++){
               for (int j=0;j<getHall(i).getSize();j++){
                   allBooksArr[cnt++] = getHall(i).getBook(j);
               }
           }
        for(int i=0;i<allBooksArr.length-1;i++){
             for (int k=0;k<allBooksArr.length-i-1;k++){
             IBook tmp = allBooksArr[k];
             double costTmp = allBooksArr[k].getCost();
             double costPlus = allBooksArr[k+1].getCost();
             if (costTmp>costPlus){
                 allBooksArr[k]=allBooksArr[k+1];
                 allBooksArr[k+1]=tmp;
                }
            } 
        }
        return allBooksArr;
       }
       
    @Override
       public void addBookToLib (int bookInd, IBook book){//добавление книги по номеру
           if (bookInd< sumAllBooks()){
               int cnt=0;
               Obj p= first;
               do{
                   if (bookInd<cnt+p.data.getSize()){
                      p.data.addBook( bookInd,book);
                   } else{
                       p=p.next;
                       cnt+=p.data.getSize();
                       if (cnt>this.hallsCount()){
                           throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
                       }
                   }
               }while(cnt!=bookInd);
           }
       }
       
    
    @Override
    public void deleteFromLib (int bookInd){
           if (bookInd< sumAllBooks()){
               int cnt=0;
               Obj p= first;
               do{
                   if (bookInd<cnt+p.data.getSize()){
                       p.data.deleteBook(bookInd);
                   } else{
                       p=p.next;
                       cnt+=p.data.getSize();
                       if(cnt>this.hallsCount()){
                           throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
                       }
                   }            
                }while (cnt!=bookInd);
            }   
       }
       
    @Override
    public double sumCost(){
        if (isEmpty()){
          return 0;
          }
          Obj p=first;
          double sum=0;
        do{
            sum+=p.data.sumCost();
        }while(p!=first);
        return sum;
    }
    @Override
       public IBook getBestBook(){
          if (isEmpty()){
          return null;
          }
          Obj p=first;
          double compare = first.data.getBestBook().getCost();
          do {
              if (p.next.data.getBestBook().getCost() > compare){
                  compare=p.data.getBestBook().getCost();
              } else{
                  p=p.next;
              }
          } while (p!=first);
          return p.data.getBestBook();
       }

   
       
    public  void outputScientificLibrary (ScientificLibrary lib, DataOutputStream out) throws IOException{
         out.writeByte(lib.hallsCount());
        for(ScientificLibraryHall hall: (ScientificLibraryHall[])lib.hallsArr()){
            hall.outputScientificHall(hall, out);
        }
     }
    
    public ScientificLibrary inputScientificLibrary (DataInputStream in) throws IOException {
          int halls=in.readInt();
          int temp []=new int[halls];
          ScientificLibrary lib = new ScientificLibrary(halls,temp);
          for (ScientificLibraryHall hall: (ScientificLibraryHall[])lib.hallsArr()){
           hall.inputScientificHall(in);
       }
          return lib;
      }
    public void writeScientificLibrary(ScientificLibrary lib, Writer out) throws IOException {
        out.write(lib.hallsCount()+"\n");
          for (ScientificLibraryHall hall: (ScientificLibraryHall[])lib.hallsArr()){
              hall.writeScientificHall(hall, out);
          }
    }
    
    public ScientificLibrary readScientificLibrary(Reader in) throws IOException {
        StreamTokenizer tknz = new StreamTokenizer(in);
      int halls=tknz.ttype;
      int temp[]=new int[halls];
      ScientificLibrary lib = new ScientificLibrary(halls,temp);
      for (ScientificLibraryHall hall: (ScientificLibraryHall[])lib.hallsArr()){
           hall.readScientificHall(in);
       
    }
      return lib;
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Scientific Library has ").append(hallsCount()).
                append(" halls\n").toString();
         for (int i=0;i<hallsCount();i++){
             str.append(this.getHall(i).toString());
         }  
         return str.toString();
    }

   

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ScientificLibrary other = (ScientificLibrary) obj;
        if (this.size != other.size) {
            return false;
        }
        if (this.hallsCnt != other.hallsCnt) {
            return false;
        }
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        return Arrays.equals(this.bookCnt, other.bookCnt);
    }
    
     @Override
    public int hashCode() {
          int hash = this.hallsCount();
        for (int i =0;i<this.hallsCount();i++){
       hash^=this.getHall(i).hashCode();
        }
        return hash;
    }
    
    @Override
    public ScientificLibrary clone(){
        try{
          ScientificLibrary newLib = (ScientificLibrary) super.clone();
          for (int i=0;i<this.hallsCount();i++){
              newLib.replaceHall(i, newLib.hallsArr()[i].clone());
          }
          return newLib;
        }catch (CloneNotSupportedException ex){
        }
        return null;
    }
}