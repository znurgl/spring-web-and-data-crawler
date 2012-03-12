package bb.crawler;

import java.util.List;

public class TwitterResponse {

	public String completed_in;
	public String max_id;
	public String max_id_str;
	public String next_page;
	public String page;
	public String query;
	public String refresh_url;
	public List<TwitterResult> results;
	public String results_per_page;
	public String since_id;
	public String since_id_str;

	public class TwitterResult {
		public String created_at;
		public String from_user;
		public String from_user_id;
		public String from_user_id_str;
		public String from_user_name;
		public Geo geo;
		public String id;
		public String id_str;
		public String iso_language_code;
		public TwitterMetadata metadata;
		public String profile_image_url;
		public String profile_image_url_https;
		public String so_urce;
		public String text;
		public String to_user;
		public String to_user_id;
		public String to_user_id_str;
		public String to_user_name;
	}

	public class Geo {
		public String[] coordinates;
		public String type;
	}

	public class TwitterMetadata {
		public String resultType;
	}

}
