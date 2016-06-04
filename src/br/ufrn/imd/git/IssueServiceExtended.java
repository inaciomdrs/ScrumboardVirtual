package br.ufrn.imd.git;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.IssueService;

public class IssueServiceExtended extends IssueService {

	public IssueServiceExtended(GitHubClient client) {
		super(client);
	}

	public List<Issue> getIssuesFromMilestone(Milestone milestone, Repository repository) {
		List<Issue> issues = new ArrayList<Issue>();

		PageIterator<Issue> pageIterator = pageIssues(repository);

		Collection<Issue> auxiliaryIssueList;
		while (pageIterator.hasNext()) {
			auxiliaryIssueList = pageIterator.next();

			int issueMilestoneNumber;
			int milestoneNumber = milestone.getNumber();
			for (Issue issue : auxiliaryIssueList) {
				if(issue.getMilestone() == null){
					continue;
				}
				issueMilestoneNumber = issue.getMilestone().getNumber();
				if (milestoneMatch(issueMilestoneNumber, milestoneNumber)) {
					issues.add(issue);
				}
			}
		}

		return issues;
	}

	public boolean milestoneMatch(int issueMilestoneNumber, int milestoneNumber) {
		return issueMilestoneNumber == milestoneNumber;
	}

}
