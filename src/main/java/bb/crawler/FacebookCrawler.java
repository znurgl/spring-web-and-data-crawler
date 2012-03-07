package bb.crawler;

import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class FacebookCrawler {

	public void search(String keyword) {

		// https://graph.facebook.com/oauth/access_token?client_id=376305552384897&client_secret=a6a6cc19b525a36e1e8d87acbccfdfe8&grant_type=client_credentials

		Facebook facebook = new FacebookTemplate(
				"376305552384897|PX7AB_xubXWZxlR6AJmUerADYpo");

		GraphApi graphApi = new FacebookTemplate();

		MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<String, String>();
		queryMap.add("q", keyword);
		queryMap.add("type", "post");
		queryMap.add("limit", "400");
		List<Reference> pages = facebook.fetchConnections("search", null,
				Reference.class, queryMap);

		System.out.println("valaszok szama: " + pages.size());

		for (Reference p : pages) {
			System.out.println(p.getId());

			try {

				String[] temp;
				temp = p.getId().split("_");

				FacebookProfile profile = facebook.userOperations()
						.getUserProfile(temp[0]);

				// Post post = facebook.feedOperations().getPost(temp[1]);

				// List<Post> post = facebook.fetchConnections(temp[0] +
				// "/posts/"
				// + temp[1], null, Post.class);

				System.out.println(profile.getFirstName());
				System.out.println(profile.getBirthday());
				System.out.println(profile.getBio());
				System.out.println(profile.getEmail());
				System.out.println(profile.getGender());
				System.out.println(profile.getLastName());
				System.out.println(profile.getPolitical());
				System.out.println(profile.getRelationshipStatus());
				System.out.println(profile.getLocation());
			} catch (Exception e) {
				System.out.println("nincs hozza jogosultsag");
			}

		}

	}

}
