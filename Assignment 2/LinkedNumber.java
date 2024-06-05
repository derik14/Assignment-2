/*
 * @author Derik Nanthamohan
 * CS1027
 * Desc: This code holds various methods that will create a doubly linked list and mutate it depending on the user's desires
 */
public class LinkedNumber{
    private int base;
    private DLNode<Digit> front;
    private DLNode<Digit> rear;
    /*
     * @param num - the number inputed by the user
     * @param baseNum - the base in which the number is
     * This constructor creates a nodes for the doubly linked list when given a base and a number
     */
    public LinkedNumber(String num, int baseNum){
        base = baseNum;
        if (num == ""){
            throw new LinkedNumberException("no digits given");
        }
        for (int y = 0; y < num.length(); y++){
            Digit object = new Digit(num.charAt(y));
            DLNode<Digit> newNode = new DLNode<Digit>(object);
            if (front == null){
                front = newNode;
                rear = newNode;
            }
            else{
                rear.setNext(newNode);
                newNode.setPrev(rear);
                rear = newNode;
            }
       }
    }
    /*
     * @param num - the number the user inputted
     * This constructor creates nodes for the doubly linked list with base 10 and the number given
     */
    public LinkedNumber(int num){
        base = 10;
        String nums = String.valueOf(num);
        if (nums == ""){
            throw new LinkedNumberException("no digits given");
        }
        for (int y = 0; y < nums.length(); y++){
            Digit object = new Digit(nums.charAt(y));
            DLNode<Digit> newNode = new DLNode<Digit>(object);
            if (front == null){
                front = newNode;
                rear = newNode;
            }
            else{
                rear.setNext(newNode);
                newNode.setPrev(rear);
                rear = newNode;
            }
        }
    }
    // This boolean method returns true if the number stored in the linked list accomodates the base
    public boolean isValidNumber(){
        boolean bool = true;
        DLNode<Digit> curDlNode;
        curDlNode = front;
        while (curDlNode != null){
            if (((curDlNode.getElement()).getValue()) >= base || ((curDlNode.getElement()).getValue()) < 0 ){
                return false;        
            }
            curDlNode = curDlNode.getNext();
        }
        return bool;
    }
    // returns base
    public int getBase(){
        return base;
    }
    // returns front node
    public DLNode<Digit> getFront(){
        return front;
    }
    // returns rear node
    public DLNode<Digit> getRear(){
        return rear;
    }
    // returns # of digits
    public int getNumDigits(){
        DLNode<Digit> curDlNode;
        curDlNode = front;
        int count = 0;
        while (curDlNode != null){
            count += 1;
            DLNode<Digit> succ = curDlNode.getNext();
            curDlNode.setNext(succ);
        }
        return count;
    }
    // converts number in linked list to string
    public String toString(){
        DLNode<Digit> curDlNode;
        curDlNode = front;
        String t = "";
        while (curDlNode != null){
            t += curDlNode.getElement();
            curDlNode = curDlNode.getNext();
        }
        return t;
    }
    /*
     * @param other - This param is another linkedlist that is used to compare
     * This method returns true if the linklist equates to the paramter
     */
    public boolean equals(LinkedNumber other){
        boolean bool = true;
        if (this.getBase() != other.getBase()){
            return false;
        } 
        if (!(this.toString().equals(other.toString()))){
            return false;
        }
        DLNode<Digit> curDlNode = front;
        int count  = 0;
		while (curDlNode != null) {
			count++;
			curDlNode = curDlNode.getNext();
        }
        DLNode<Digit> otherDlNode = other.getFront();
        int count2  = 0;
		while (otherDlNode != null) {
            count2++;
			otherDlNode = otherDlNode.getNext();
        }
        if (count != count2){
            return false;
        }
        return bool;
    }
    /*
     * @param newBase - the new base the number will be converted too
     * This method converts the number in the linkedlist to a new base
     */
    public LinkedNumber convert(int newBase){
        if (this.isValidNumber()){
            if (newBase != 10 && base == 10){
                return new LinkedNumber(decToNondec(newBase), newBase);
            }
            else if(newBase == 10 && base != 10){
                return new LinkedNumber(String.valueOf(nondecToDec()), newBase);
            }
            else if (newBase != 10 && base != 10){
                return new LinkedNumber(nondecToNondec(newBase), newBase);
            }
        }
        else{
            throw new LinkedNumberException("cannot convert invalid number");
        }
        return this;
    }
    /*
     * @param newBase - base being used for conversion
     * Private helper method that returns decimal to non decimal conversion
     */
    private String decToNondec(int newBase){
        int val = 0;
		DLNode<Digit> curDlNode = rear;
        int count  = 0;
        String f = "";
		while (curDlNode != null) {
			int inVal = (curDlNode.getElement()).getValue();
            val += inVal * (Math.pow(10, count));
            count++;
			curDlNode = curDlNode.getPrev();
		}
        while (val != 0){
            int rem = val % newBase;
            val = val / newBase;
            if (rem == 10){
                f += "A";
            }
            else if(rem == 11){
                f += "B";
            }
            else if(rem == 12){
                f += "C";
            }
            else if(rem == 13){
                f += "D";
            }
            else if(rem == 14){
                f += "E";
            }
            else if(rem == 15){
                f += "F";
            }
            else{
                f += String.valueOf(rem);
            }
        }
        String t = "";
        for (int x = f.length()-1; x >=0; x--){
            t += f.charAt(x);
        }
        return t;
	}
    /*
     * @param newBase - base being used for conversion
     * Private helper method that returns non decimal to decimal conversion
     */
    private int nondecToDec(){
        int val = 0;
        DLNode<Digit> curDlNode = rear;
        int count  = 0;
		while (curDlNode != null) {
			int inVal = curDlNode.getElement().getValue();
            val += inVal * (Math.pow(base, count));
			curDlNode = curDlNode.getPrev();
            count++;
		}
        return val;
    }
    /*
     * @param newBase - base being used for conversion
     * Private helper method that returns non decimal to non decimal conversion
     */
    private String nondecToNondec(int newBase){
        int temp = 0;
        String f = "";
        temp = nondecToDec();
        while (temp != 0){
            int rem = temp % newBase;
            temp = temp /newBase;
            if (rem == 10){
                f += "A";
            }
            else if(rem == 11){
                f += "B";
            }
            else if(rem == 12){
                f += "C";
            }
            else if(rem == 13){
                f += "D";
            }
            else if(rem == 14){
                f += "E";
            }
            else if(rem == 15){
                f += "F";
            }
            else{
                f += String.valueOf(rem);
            }
        }
        String t = "";
        for (int x = f.length() - 1; x >= 0; x--){
            t += f.charAt(x);
        }
        return t;
    }
    /*
     * @param digit - number being added to linkedlist
     * @param position - position in the linkedlist
     * void  method that adds a digit to linked list
     */
    public void addDigit(Digit digit, int position){
        DLNode<Digit> curDlNode = front;
        int count = 0;
		while (curDlNode != null) {
			curDlNode = curDlNode.getNext();
            count++;
        }
        if (position >= 0 && position <= count + 1){
            DLNode<Digit> newNode = new DLNode<Digit>(digit);
            if (position == 0){
                newNode.setPrev(rear);
                newNode.setNext(null);
                rear.setNext(newNode);
                rear = newNode;
                count++;
            }
            else if (position == count){
                newNode.setNext(front);
                front.setPrev(newNode);
                front = newNode;
                count++;
            }
            else{
                curDlNode = front;
                int count2 = count-1;
                int temp = count2;
                while (curDlNode != null && temp > 0) {
                    temp--;
                    curDlNode = curDlNode.getNext();
                    if (temp == position){
                        break;
                    }
                }
                newNode.setNext(curDlNode.getNext());
                curDlNode.setNext(newNode);
                newNode.setPrev(curDlNode);
                newNode.getNext().setPrev(newNode);
                count++;
                curDlNode = rear;
                while (curDlNode != null){
                    curDlNode = curDlNode.getPrev();
                }
            } 
        }
        else{
            throw new LinkedNumberException("invalid position");
        }
    }
    /*
     * @param position - position in linkedlist
     * int method that removes certain digitd and returns the numeric place value of the digit being removed
     */
    public int removeDigit(int position){
        DLNode<Digit> curDlNode = front;
        int count = 0;
		while (curDlNode != null) {
			count++;
			curDlNode = curDlNode.getNext();
        }
        curDlNode = rear;
        int remove = 0;
        int val = 0;
        int count5 = 0;
        if (position < 0 || position > count){
            throw new LinkedNumberException("invalid position");
        }
        else{
            if (position == 0){
                curDlNode = rear;
            }
            else{
                for (int x = 0; x < position; x++){
                    curDlNode = curDlNode.getPrev();
               }
            }   
           val = curDlNode.getElement().getValue();
           int divisor = (int)Math.pow(10,position);
           remove = (val * divisor);
           if (base != 10){
                String r = Integer.toString(remove);
                int conver = 0;
                int count2 = 0;
                int length= r.length()-1;
                int temp = length;
                while(count2 <= length && length >= 0){
                    int multiplier = (int)Math.pow(base, temp);
                    conver += (Integer.parseInt(String.valueOf(r.charAt(count2)))) * (multiplier);
                    count2++;
                    temp--;
                }
                if (position == count - 1){
                    curDlNode = front;
                    curDlNode.getNext().setPrev(null);
                    front = curDlNode.getNext();
                    curDlNode = rear; 
                }
                else if(position == 0){
                    curDlNode.getPrev().setNext(null);
                    rear = curDlNode.getPrev();
                    curDlNode = rear;
                } else{
                    curDlNode = rear;
                    while (curDlNode != null) {
                        if (count5 == position) {
                            curDlNode.getNext().setPrev(curDlNode.getPrev());
                            curDlNode.getPrev().setNext(curDlNode.getNext());
                            count5++;
                            curDlNode = curDlNode.getPrev();
                        } else {
                            count5++;
                            curDlNode = curDlNode.getPrev();
                        }
                    }
                    curDlNode = rear; 
                    while (curDlNode != null) {
                        curDlNode = curDlNode.getPrev();
                    }
                }
                return conver;
            }
        }
        return remove;
    }
}