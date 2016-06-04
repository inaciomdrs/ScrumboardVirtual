package br.ufrn.imd.git;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_USER;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.CollaboratorService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.google.gson.reflect.TypeToken;

public class RepositoryServiceExtended extends RepositoryService {
	
	private CollaboratorService collaboratorService;
	
	public RepositoryServiceExtended(GitHubClient client){
		super(client);
		collaboratorService = new CollaboratorService(client);
	}
	
	public List<Repository> getRepositoriesUserParticipate() {
		try {
			return getAll(pageRepositories());
		} catch (IOException e) {
			return null;
		}
	}
	
	public PageIterator<Repository> pageRepositories() {
		PagedRequest<Repository> request = createPagedRequest();
		request.setUri(SEGMENT_USER + SEGMENT_REPOS);
		request.setType(new TypeToken<List<Repository>>() {
		}.getType());
		return createPageIterator(request);
	}
	
	public boolean addCollaboratorToRepository(User collaborator, Repository repository){	
		try {
			collaboratorService.addCollaborator(repository, collaborator.getLogin());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}
