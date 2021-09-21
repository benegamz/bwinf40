import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class main
{
    String alphabet[]="ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines=new ArrayList<String>();
    String finalSolution[];
    String preSolution[][];
    String straight[];
    String parallel[];
    public main()
    {
        /* Reading File */
        File file = new File("parkplatz0.txt");
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.println(lines);
        /* Getting each char, line by line  */
        String firstln[]=lines.get(0).split(" ");
        String thirdln[]=lines.get(2).split(" ");
        /* Filling array with the normal-cars */
        for(int i=0;i<alphabet.length;i++){
            if(alphabet[i].equalsIgnoreCase(firstln[1])){
                straight=new String[i+1];
                parallel=new String[i+1];
                for(int j=0;j<straight.length;j++){
                    straight[j]=alphabet[j];
                }
            }
        }
        /* Filling array with the sideways-cars */
        for(int i=2;i<lines.size();i++){
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
        }
        finalSolution=new String[straight.length];
        preSolution=new String[straight.length][straight.length * straight.length];
    }

    public void AutosAusparken()
    {   
        for(int i=0;i<straight.length;i++){
            if(straight[i]==null){
                break;
            }
            finalSolution[i]=straight[i]+": ";
            moveOut(i);
            resetparallel();
        }
        printResult();
    }

    public boolean moveRight(int index, int distance){
        /* Returncodes: -1-> error, 1->done */
        try{
            String parallelName = parallel[index];
            if(parallelName==null){
                return false;
            }
            if((index - 1)<0){
            }
            else if(parallel[index-1]==parallelName){
                if(parallel[index+1]==null){
                    parallel[index+1]=parallelName;
                    parallel[index-1]=null;
                    return true;
                }
                else if(parallel[index+1]!=null&&parallel[index+1]!=parallelName){
                    if(!moveRight(index+1, distance)) {
                        return false;
                    }
                    parallel[index+1] = parallelName;
                    parallel[index-1] = null;
                    return true;
                }
            }
            if((index + 2)>parallel.length){
            }
            else if(parallel[index+1]==parallelName){
                if(parallel[index+2]==null){
                    parallel[index + 2]=parallelName;
                    parallel[index]=null;
                    return true;
                }
                else if(parallel[index+2]!=null&&parallel[index+2]!=parallelName){
                    if(!moveRight(index+2, distance)) {
                        return false;
                    }
                    parallel[index + 2]=parallelName;
                    parallel[index]=null;
                    return true;
                }
            }
            return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean moveLeft(int index, int distance){
        /* Returncodes: -1-> error, 1->done */
        try{
            String parallelName=parallel[index];
            if(parallelName==null){ //if there isnt a car at index returns error
                return false;
            }
            if((index - 2)<0){      
            }
            else if(parallel[index - 1]==parallelName){
                if(parallel[index-2]==null){
                    parallel[index - 2]=parallelName;
                    parallel[index]=null;
                    return true;
                }
                else if(parallel[index-2]!=null&&parallel[index-2]!=parallelName){
                    if(!moveLeft(index-2, distance)) {
                        return false;
                    }
                    parallel[index-2] = parallelName;
                    parallel[index] = null;
                    return true;
                }
            }
            if((index + 1)>parallel.length){
            }
            else if(parallel[index + 1]==parallelName){
                if(parallel[index-1]==null){
                    parallel[index - 1]=parallelName;
                    parallel[index + 1]=null;
                    return true;
                }
                else if(parallel[index-1]!=null&&parallel[index-1]!=parallelName){
                    if(!moveLeft(index-1, distance)) {
                        return false;
                    }
                    parallel[index-1] = parallelName;
                    parallel[index+1] = null;
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }

    public void resetparallel(){
        for(int i=0;i<parallel.length;i++){
            parallel[i]=null;
        }
        for(int i=2;i<lines.size();i++){
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
        }
    }

    public void printResult(){
        for(int x=0;x<straight.length;x++){
            if(finalSolution[x]==null){
                break;
            }
            else{
                System.out.println(finalSolution[x]);
            }
        }
    }

    public void moveOut(int index){
        String parallelName=parallel[index];
        try{
            if(parallel[index]==null){
            }
            else{
                if((index - 1)<0){
                    return;
                }
                else if(parallel[index - 1]==parallelName){
                    if((index-2)<0){
                    }
                    else if(parallel[index-2]==null){
                        moveLeft(index, 1);
                        finalSolution[index]+=parallelName+" 1 left";
                        preSolution[index][0]="-1";
                        return;
                    }
                    if((index+2)>parallel.length){
                    }
                    else if(parallel[index+1]==null&&parallel[index+2]==null){
                        moveRight(index, 2);
                        finalSolution[index]+=parallelName+" 2 right";
                        preSolution[index][0]="1";
                        return;
                    }
                }
                if((index + 1)>=parallel.length){
                }
                else if(parallel[index + 1]==parallelName){
                    if((index+2)>parallel.length){
                    }
                    else if(parallel[index+2]==null){
                        moveRight(index, 1);
                        finalSolution[index]+=parallelName+" 1 right";
                        preSolution[index][0]="1";
                        return;
                    }
                    if((index-2)<0){
                    }
                    else if(parallel[index-1]==null&&parallel[index-2]==null){
                        moveLeft(index, 2);
                        finalSolution[index]+=parallelName+" 2 left";
                        preSolution[index][0]="-1";
                        return;
                    }
                }
            }
        }
        catch(Exception e){
            return;
        }
    }
    public int test(){
        return Arrays.asList(parallel).indexOf("H");
    }
}
