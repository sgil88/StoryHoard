/**
 * Copyright 2013 Alex Wong, Ashley Brown, Josh Tate, Kim Wu, Stephanie Gil
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package ca.ualberta.cmput301f13t13.storyhoard.test;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.c301f13t13.backend.*;
import ca.ualberta.cs.c301f13t13.gui.*;

import android.content.Intent;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Class meant for the testing of the MediaManager class in the StoryHoard
 * application.
 * 
 * @author Stephanie Gil
 * 
 * @see MediaManager
 */
public class TestMediaManager extends
		ActivityInstrumentationTestCase2<EditChapterActivity> {
	private static Uri uri;
	private MediaManager mm = null;
	private static EditChapterActivity activity;
	
	public TestMediaManager() {
		super(EditChapterActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// Clearing database
		DBHelper helper = DBHelper.getInstance(this.getActivity());
		helper.close();
		this.getActivity().deleteDatabase(DBContract.DATABASE_NAME);
		
		mm = MediaManager.getInstance(getActivity());
		
		Story story = new Story("title", "author", "es", "432432");
		Intent intent = new Intent();
		intent.putExtra("isEditing", false);
		intent.putExtra("addingNewChapt", true);
		intent.putExtra("Story", story);
		intent.putExtra("Chapter", new Chapter(story.getId(), null));
		
		setActivityIntent(intent);
		
		activity = getActivity();
		activity.takePhoto();
		uri = activity.getImageFileUri();		
	}

	/**
	 * Tests adding and loading an image.
	 */
	public void testAddLoadImage() {
		Chapter chap = activity.getChapter();
		Media mockMedia = new Media(null, chap.getId(), null, null);

		ArrayList<Object> objects = mm.retrieve(mockMedia);
		assertEquals(objects.size(), 1);

		Media check = (Media) objects.get(0);
		assertTrue(check.getBitmap() != null);
	}

	/**
	 * Tests getting all photos, and all illustrations belonging to a chapter.
	 */
	public void testGetAllMedia() {
		activity.takePhoto();
		Uri uri2 = activity.getImageFileUri();
		UUID chapId = UUID.randomUUID();
		
		Media m2 = new Media(chapId, uri2.getPath(), Media.PHOTO);

		mm.insert(m2);

		// get all media
		Media criteria = new Media(null, chapId, null, null);
		ArrayList<Object> objects = mm.retrieve(criteria);
		assertEquals(objects.size(), 1);

		Media newm = (Media) objects.get(0);
		assertTrue(newm.getBitmap() != null);
	}

	/**
	 * Tests updating a chapter's media.
	 */
	public void testUpdateMedia() {
		Chapter chap = activity.getChapter();
		Media criteria = new Media(null, chap.getId(), null, null);
		
		ArrayList<Object> objects = mm.retrieve(criteria);
		assertEquals(objects.size(), 1);

		Media newM1 = (Media) objects.get(0);

		newM1.setType(Media.ILLUSTRATION);
		
		activity.takePhoto();
		Uri uri2 = activity.getImageFileUri();
		
		newM1.setPath(uri2.getPath());

		mm.update(newM1);

		// make sure you can find new chapter
		objects = mm.retrieve(newM1);
		assertEquals(objects.size(), 1);
		newM1 = (Media) objects.get(0);

		assertFalse(newM1.getType().equals(Media.PHOTO));
		assertFalse(newM1.getPath().equals(uri.getPath()));
	}
}
