package br.ufrn.imd.git;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CollaboratorService;
import org.eclipse.egit.github.core.service.UserService;

public class UserServiceExtended extends UserService {

	private CollaboratorService collaboratorService;
	
	public UserServiceExtended(GitHubClient client) {
		super(client);
		collaboratorService = new CollaboratorService(client);
	}

	public List<User> getCollaboratorsOfRepository(Repository repository) {		
		
		try {
			return collaboratorService.getCollaborators(repository);
		} catch (IOException e) {
			return null;
		}
	}

}
