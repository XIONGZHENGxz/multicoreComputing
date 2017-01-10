import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
class FreqCallable implements Callable<Integer>{
	int x,start,end;
	int[] a;
	public FreqCallable(int i,int s,int e,int[] a){
		this.x=i;
		this.a=a;
		this.start=s;
		this.end=e;
	}
	public Integer call(){
		try{
			int count=0;
			for(int j=start;j<=end;j++){
				if(a[j]==x) count++;
			}
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
}

public class Frequency{
	public int parallelFreq(int x,int[] A,int numOfThreads){
		int l=A.length/numOfThreads;
		int ans=0;
		try{
		ExecutorService pool = Executors.newFixedThreadPool(numOfThreads);
		for(int i=0;i<numOfThreads-1;i++){
			Callable<Integer> fc=new FreqCallable(x,i*l,(i+1)*l,A);
			Future<Integer> future=pool.submit(fc);
			ans+=future.get();
		}
		int last=A.length-(l+1)*(numOfThreads-1);
		Callable<Integer> fc=new FreqCallable(x,last,A.length-1,A);
		Future<Integer> future=pool.submit(fc);
		ans+=future.get();
		}catch(Exception e){
			System.err.println(e);
		}
		return ans;
	}

	public static void main(String...args){
		int x=1;
		int[] a=new int[]{1,1,2,2,3,1,3,1,4,5,1,5};
		int numOfThreads=3;
		Frequency f=new Frequency();
		System.out.println(f.parallelFreq(x,a,numOfThreads));
	}
}
