import com.dreamfactory.api.DbApi;
import com.dreamfactory.api.UserApi;
import com.dreamfactory.client.ApiException;
import com.dreamfactory.model.ComponentList;
import com.dreamfactory.model.Login;
import com.dreamfactory.model.Session;

public class Demo {

	public static void main(String[] args) throws ApiException {
		UserApi userApi = new UserApi();
		userApi.addHeader("X-DreamFactory-Application-Name", "Spyder");
		userApi.setBasePath("https://dsp-spyder.cloud.dreamfactory.com/rest");
		Login login = new Login();
		login.setEmail("pp2112@ic.ac.uk");
		login.setPassword("login_test123");
		try {
			Session session = userApi.login(login);
			String session_id = session.getSession_id();
			DbApi dbApi = new DbApi();
			dbApi.addHeader("X-DreamFactory-Application-Name", "Spyder");
			dbApi.addHeader("X-DreamFactory-Session-Token", session_id);
			dbApi.setBasePath("https://dsp-spyder.cloud.dreamfactory.com/rest"); // your dsp url
			// And here is how to get to do records, Please check function
			// getRecords to see how to utilize different parameters
			ComponentList resources = dbApi.getTables(true,false);
			System.out.println(resources);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
