package br.ufrn.imd.test;

import java.net.MalformedURLException;
import java.net.URL;

public class StringSplitTest {

	public static void main(String[] args) {
		String PATH_SEPARATOR = "/";
		
		URL repositoryUrl = null;

		try {
			repositoryUrl = new URL("https://api.github.com/repos/inaciomdrs/Scrumboard-Virtual/milestones/1");
		} catch (MalformedURLException e) {
			System.exit(1);
		}
		
		System.out.println(repositoryUrl.getPath());
		
		String[] repositoryURLpath = repositoryUrl.getPath().split(PATH_SEPARATOR);
		
		for(int i = 0; i < repositoryURLpath.length; i++){
			System.out.println(i + " - " + repositoryURLpath[i]);
		}
		
		System.out.println(repositoryURLpath[3]);
		

	}

}
