package  com.sooncode.jdbc4json;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sooncode.jdbc4json.entity.Clazz;
import com.sooncode.jdbc4json.entity.Student;
import com.sooncode.jdbc4json.page.One2One;

 
/**
 * 
 * @author pc
 *
 */
public class TimeTest {

	private static Logger logger =   Logger.getLogger(TimeTest.class);
	public static void main(String[] args) {
		/*long startMem = Memory.used();
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < 6000; i++) {
			double r = Math.random();
			map.put("jfslsdsdfsdfsdfsdfs" + r, "jfsldsfsdfsfsfdsfsd" + r);
		}
		long endMem = Memory.used();
		logger.info("【执行方法】  消耗的内存： " + (endMem - startMem) + " (bytes) / " + (endMem - startMem) / 8 + " (B) / " + (endMem - startMem) / (8 * 1024) + " (kB)");*/
	 
	
		 
			Student s = new Student();
			s.setName("AA");
			Clazz c = new Clazz();
			c.setClazzName("高一（2）班");
			One2One o = new One2One();
			o.add(s);
			o.add(c);
			
			Student st = o.getOne(Student.class);
			
			System.out.println(st.getName());
		 
	
	
	
	
	}
	
	
	
	
	
}
