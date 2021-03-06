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

import java.util.UUID;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301f13t13.storyhoard.R;
import ca.ualberta.cs.c301f13t13.backend.ObjectType;
import ca.ualberta.cs.c301f13t13.backend.SHController;
import ca.ualberta.cs.c301f13t13.backend.Story;
import ca.ualberta.cs.c301f13t13.backend.Utilities;

/**
 * Activity for editing the story metadata (title, author, description,
 * and cover image).
 * 
 * @author Alexander Wong
 * 
 */
public class EditStoryActivity extends Activity {
	private EditText newTitle;
	private EditText newAuthor;
	private EditText newDescription;
	private Button addfirstChapter;
	private Button addStoryImage;
	private Story newStory;
	private SHController gc;
	private boolean isEditing;
	private AlertDialog imageDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_story);

		final ActionBar actionBar = getActionBar();
		actionBar.setTitle("Story Metadata");
		actionBar.setDisplayShowTitleEnabled(true);

		gc = SHController.getInstance(this);

		newTitle = (EditText) findViewById(R.id.newStoryTitle);
		newAuthor = (EditText) findViewById(R.id.newStoryAuthor);
		newDescription = (EditText) findViewById(R.id.newStoryDescription);
		addfirstChapter = (Button) findViewById(R.id.addFirstChapter);
		addStoryImage = (Button) findViewById(R.id.addStoryImage);

		// Check if we are editing the story or making a new story
		Bundle bundle = this.getIntent().getExtras();
		isEditing = bundle.getBoolean("isEditing", false);
		if (isEditing) {
			newStory = gc.getCompleteStory((UUID) bundle.get("storyID"));
			newTitle.setText(newStory.getTitle());
			newAuthor.setText(newStory.getAuthor());
			newDescription.setText(newStory.getDescription());
			addfirstChapter.setText("Save Metadata");
		}

		addfirstChapter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * Save all text forms to first story Switch to first chapter
				 * creation activity
				 */
				String title = newTitle.getText().toString();
				String author = newAuthor.getText().toString();
				String description = newDescription.getText().toString();
				if (isEditing) {
					newStory.setAuthor(author);
					newStory.setTitle(title);
					newStory.setDescription(description);
					gc.updateObject(newStory, ObjectType.CREATED_STORY);
				} else {
					newStory = new Story(title, author, description, 
							Utilities.getPhoneId(getBaseContext()));
					Intent intent = new Intent(EditStoryActivity.this,
							EditChapterActivity.class);
					intent.putExtra("isEditing", false);
					intent.putExtra("newStory", true);
					intent.putExtra("storyID", newStory.getId());
					intent.putExtra("story", newStory);
					startActivity(intent);
				}
				finish();
			}
		});

		/*
		 * IMPLEMENTATION NOT READY TO GO YET. COMMENTING OUT AND TOASTING NON
		 * IMPLEMENTED MESSAGE HERE.
		 */
		addStoryImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// AlertDialog.Builder alert = new AlertDialog.Builder(context);
				// // Set dialog title
				// alert.setTitle("Choose method:");
				// // Options that user may choose to add photo
				// final String[] methods = { "Take Photo",
				// "Choose from Gallery" };
				// alert.setSingleChoiceItems(methods, -1,
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog, int item) {
				// switch (item) {
				// case 0:
				// // Take a photo
				// break;
				// case 1:
				// // Choose from gallery
				// break;
				// }
				// imageDialog.dismiss();
				// }
				// });
				// imageDialog = alert.create();
				// imageDialog.show();
				Toast.makeText(getBaseContext(),
						"Not implemented this iteration", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}
