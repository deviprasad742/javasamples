package gen;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceQueueTest {

	private static Object strongRef = null;
	private int var = 2;
	
	
	public static void main(String[] args) {
		ReferenceQueueTest ref = new ReferenceQueueTest();
		ReferenceQueue referenceQueue = new ReferenceQueue();
		WeakReference<ReferenceQueueTest> weakReference = new WeakReference<ReferenceQueueTest>(ref, referenceQueue);
		ref = null;
		System.gc();
		Reference fromQue = referenceQueue.poll();
		System.out.println(fromQue);
		
		ReferenceQueue rq= new ReferenceQueue(); 
		String s="hello";
		Reference r= new PhantomReference(s,rq); 
		System.out.println(rq.poll());
		s=null;
		System.gc();	
		System.out.println(rq.poll());
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		strongRef = this;
	}
	
	
	@Override
	public String toString() {
		return super.toString();
	}
}
