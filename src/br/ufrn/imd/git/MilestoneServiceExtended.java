package br.ufrn.imd.git;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.eclipse.egit.github.core.service.RepositoryService;

public class MilestoneServiceExtended extends MilestoneService {

	private final String PATH_SEPARATOR;
	private RepositoryService repositoryService;
	private final int REPOSITORY_OWNER_POSITION_IN_PATH;
	private final int REPOSITORY_NAME_POSITION_IN_PATH;

	public MilestoneServiceExtended(GitHubClient client) {
		super(client);
		repositoryService                 = new RepositoryService(client);
		PATH_SEPARATOR                    = "/";
		REPOSITORY_OWNER_POSITION_IN_PATH = 2;
		REPOSITORY_NAME_POSITION_IN_PATH  = 3;
	}

	// >>
	// https://api.github.com/repos/inaciomdrs/Scrumboard-Virtual/milestones/1

	public Repository getRepositoryOfMilestone(Milestone milestone) {
		URL repositoryUrl = null;
		Repository repository;

		try {
			repositoryUrl = new URL(milestone.getUrl());
		} catch (MalformedURLException e) {
			return null;
		}

		String[] repositoryURLpath = repositoryUrl.getPath().split(PATH_SEPARATOR);
		
		String repositoryOwner = repositoryURLpath[REPOSITORY_OWNER_POSITION_IN_PATH];
		String repositoryName  = repositoryURLpath[REPOSITORY_NAME_POSITION_IN_PATH];
		
		try {
			repository = repositoryService.getRepository(repositoryOwner, repositoryName);
		} catch (IOException e) {
			repository = null;
		}
				
		return repository;
	}

}
