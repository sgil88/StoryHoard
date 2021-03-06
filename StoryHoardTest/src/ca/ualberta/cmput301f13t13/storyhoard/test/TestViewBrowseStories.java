/**
 * Copyright 2013 Alex Wong, Ashley Brown, Josh Tate, Kim Wu, Stephanie Gil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.ualberta.cmput301f13t13.storyhoard.test;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.GridView;
import ca.ualberta.cmput301f13t13.storyhoard.R;
import ca.ualberta.cs.c301f13t13.backend.Story;
import ca.ualberta.cs.c301f13t13.gui.AdapterStories;
import ca.ualberta.cs.c301f13t13.gui.ViewBrowseStories;

/**
 * Test case for the activity to view all stories
 * 
 * @author Alex Wong
 * 
 */
public class TestViewBrowseStories extends
		ActivityInstrumentationTestCase2<ViewBrowseStories> {
	private GridView gridView;
	private ArrayList<Story> gridArray = new ArrayList<Story>();
	private AdapterStories customGridAdapter;
	private ViewBrowseStories activity;
	
	public TestViewBrowseStories() {
		super(ViewBrowseStories.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		Intent intent = new Intent();
		intent.putExtra("isEditing", false);
		intent.putExtra("storyId", UUID.randomUUID());
		
		setActivityIntent(intent);
		
		activity = getActivity();
		gridView = (GridView) activity.findViewById(R.id.gridStoriesView);
	}

	/**
	 * Testing that the preconditions setup properly
	 */
	public void testPreConditions() {
		assertTrue(gridView != null);
	}
}
