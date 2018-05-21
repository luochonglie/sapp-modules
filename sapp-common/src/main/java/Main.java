import com.demo.perform.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		Performer p = context.getBean("performer", Performer.class);
		p.perform();
		p = context.getBean("duke", Performer.class);
		p.perform();

	}

}
