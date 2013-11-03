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
package ca.ualberta.cs.c301f13t13.gui;

/**
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
import java.util.UUID;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301f13t13.storyhoard.R;
import ca.ualberta.cs.c301f13t13.backend.GeneralController;
import ca.ualberta.cs.c301f13t13.backend.Story;

/**
 * Handles viewing the story metadata.
 * 
 * @author alexanderwong
 * 
 */
public class ViewBrowseStory extends Activity {
	Story focusedStory;
	GeneralController gc;

	private ImageView storyCover;
	private TextView storyTitle;
	private TextView storyAuthor;
	private TextView storyDescription;
	private Button beginReading;
	private UUID storyID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_browse_story);

		final ActionBar actionBar = getActionBar();
		actionBar.setTitle("Story Metadata");
		actionBar.setDisplayShowTitleEnabled(true);

		storyCover = (ImageView) findViewById(R.id.storyImage);
		storyTitle = (TextView) findViewById(R.id.storyTitle);
		storyAuthor = (TextView) findViewById(R.id.storyAuthor);
		storyDescription = (TextView) findViewById(R.id.storyDescription);
		beginReading = (Button) findViewById(R.id.viewFirstChapter);

		// Initialize the general controller and grab the story
		gc = GeneralController.getInstance();
		Bundle bundle = this.getIntent().getExtras();
		storyID = (UUID) bundle.getSerializable("storyID");
		focusedStory = gc.getCompleteStory(storyID, this);
		
		// Initialize the read button
		beginReading.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Begin reading, go to first chapter
				Intent intent = new Intent(getBaseContext(), ViewChapterActivity.class);
				intent.putExtra("storyID", storyID);
				intent.putExtra("chapterID", focusedStory.getFirstChapterId());
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		focusedStory = gc.getCompleteStory(storyID, this);
		storyCover.setImageBitmap(focusedStory.getImage());
		storyTitle.setText(focusedStory.getTitle());
		storyAuthor.setText(focusedStory.getAuthor());
		storyDescription.setText(focusedStory.getDescription());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view_browse_story, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {
		case R.id.editExistingStory:
			// Handle editing the story content, like chapters and choices
			// intent = new Intent(this, EditStoryActivity.class);
			// startActivity(intent);
			Toast.makeText(this, "Story Editing is not implemented",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.editStoryMetaData:
			intent = new Intent(this, EditStoryActivity.class);
			intent.putExtra("isEditing", true);
			intent.putExtra("storyID", focusedStory.getId());
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}