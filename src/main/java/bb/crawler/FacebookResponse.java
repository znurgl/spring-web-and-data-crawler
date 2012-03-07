package bb.crawler;

import java.util.List;

public class FacebookResponse {

	public List<FBData> data;

	public Page page;

	public class Likes {
		public List<From> from;
		public Integer count;
	}

	public class From {
		public String name;
		public String id;
	}

	public class FBData {
		public String id;
		public From from;
		public String message;
		public String picture;
		public String link;
		public String name;
		public String caption;
		public String description;
		public String source;
		public List<Properties> properties;
		public String icon;
		public List<Actions> actions;
		public String type;
		public Likes likes;
		public String story;
		// public String story_tags;
		public Long object_id;
		public Application aplication;
		public String created_time;
		public String updated_time;

		@Override
		public String toString() {
			return "FBData [id=" + id + ", from=" + from + ", message="
					+ message + ", picture=" + picture + ", link=" + link
					+ ", name=" + name + ", caption=" + caption
					+ ", description=" + description + ", source=" + source
					+ ", properties=" + properties + ", icon=" + icon
					+ ", actions=" + actions + ", type=" + type + ", likes="
					+ likes + ", story=" + story + ", object_id=" + object_id
					+ ", aplication=" + aplication + ", created_time="
					+ created_time + ", updated_time=" + updated_time + "]";
		}

	}

	public class Application {
		public String name;
		public String id;
	}

	public class Actions {
		public String name;
		public String link;
	}

	public class Properties {
		public String name;
		public String text;
		public String href;
	}

	public class StoryTagItem {
		public String id;
		public String name;
		public String offset;
		public String length;
	}

	public class Page {
		public String previous;
		public String next;
	}

}
