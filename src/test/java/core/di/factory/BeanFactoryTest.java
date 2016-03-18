package core.di.factory;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import di.examples.MyQnaService;
import di.examples.MyUserController;
import di.examples.MyUserService;
import di.examples.QnaController;

public class BeanFactoryTest {
	private BeanFactory beanFactory;

	@Before
	public void setup() {
		beanFactory = new BeanFactory();
		ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
		scanner.doScan("di.examples");
		beanFactory.initialize();
	}

	@Test
	public void constructorDI() throws Exception {
		QnaController qnaController = beanFactory.getBean(QnaController.class);

		assertNotNull(qnaController);
		assertNotNull(qnaController.getQnaService());

		MyQnaService qnaService = qnaController.getQnaService();
		assertNotNull(qnaService.getUserRepository());
		assertNotNull(qnaService.getQuestionRepository());
	}

	@Test
	public void fieldDI() throws Exception {
		MyUserService userService = beanFactory.getBean(MyUserService.class);
		assertNotNull(userService);
		assertNotNull(userService.getUserRepository());
	}

	@Test
	public void setterDI() throws Exception {
		MyUserController userController = beanFactory.getBean(MyUserController.class);

		assertNotNull(userController);
		assertNotNull(userController.getUserService());
	}

	@After
	public void tearDown() {
		beanFactory.clear();
	}
}
