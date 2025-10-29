interface DoesGo {
    public void go() ;
}


// adding Program4 :
/* add a class Program4
 * add a switch case
 * Done !!
 */

class ProgramFactory {

    public static DoesGo getProgram(int index){
        switch (index) {
            case 1:
                return new Program1() ;
            case 2 :
                return new Program2() ;
            case 3 :
                return new Program3() ;
            default:
                return new Program1(); // default
        }
    }
}

public class Client {

    public static void main(String args[]) {
        int index = Integer.parseInt(args[0]) ;
        
        DoesGo program = ProgramFactory.getProgram(index) ;
        program.go();
    }

}
