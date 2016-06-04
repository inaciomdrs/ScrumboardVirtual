package br.ufrn.imd.test;

import java.io.IOException;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

public class GitTest {
	
	public static void main(String[] args) throws IOException {
		GitHubClient client = new GitHubClient();
		UserService service = new UserService(client);
		
		User user = service.getUser("inaciomdrs");
		System.out.println(user.getName());
		
		
		
	}

}
