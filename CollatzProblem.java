public class CollatzProblem {
    public static void f(CollatzSeq cs, long n, int x){
      
        int pow=1;
        for(int i=0;i<x;i++){
            pow*=3;
        }
        if(4*pow==n || 2*pow==n || pow==n){
            //mach mal *pow
            return;
        }else{
            if(n>0 && (n%2)==0){
                cs.add(n);
                f(cs,n/2,x);


            }else if(n>0 && (n%2!=0)){
                cs.add(n);
                f(cs,3*n+pow,x);

            }
        }

    }
}
