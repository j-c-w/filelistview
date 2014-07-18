package com.jcw.andriod.fileListView;/*
 * Author - Woodruff
 * 
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

public class FileListItemView extends RelativeLayout {
	private TextView fileName;
	private TextView metadata;
	private ImageView icon;


	public FileListItemView(Context context) {
		super(context);
		init();
	}

	public FileListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FileListItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.file_list_item, FileListItemView.this);

		fileName = (TextView)findViewById(R.id.fileName);
		metadata = (TextView)findViewById(R.id.metaInfo);
		icon = (ImageView)findViewById(R.id.icon);
	}

	/*
	returns a string that shows the directory that this
	item represents.

	However, it only returns the **lowest level**, i.e.
	for the folder "storage/sd/example", this would return
	"example"
	 */
	public String getRepresentedDir() {
		return fileName.getText().toString();
	}

	public void setFileName(String fileName) {
		this.fileName.setText(fileName);
	}

	public void setMetadata(String metadata) {
		this.metadata.setText(metadata);
	}

	public void setIcon(int resId) {
		this.icon.setImageResource(resId);
	}

	public void setFile(File file) {
		setFileName(file.getName());
		setMetadata(getMetadataText(file));
		setIcon(getPictureId(file));
	}


	/*
	returns by default the size of the file if the
	this represents a file and an empty string if
	 this represents a folder
	 */
	public String getMetadataText(File file) {
		if (file.isFile()) {
			return formatBytes(file.length());
		} else {
			return "";
		}
	}

	/*
	returns the res id of the icon that
	should be displayed for this type of
	file -- this is an area open to improvement
	(with different icons for different file types
	 */
	private int getPictureId(File file) {
		if (file.isDirectory()) {
			return R.drawable.directory_icon;
		} else {
			return R.drawable.file_icon;
		}
	}


	/*
	formats a large number of bytes into a normal
	representation (i.e. kB, MB, GB etc.)
	 */

	private static String formatBytes(long byteCount) {
		//keeps track of which postfix is needed
		int index = 0;
		//keeps track of the last 3 digits to display after the
		//decimal point
		int postDecimalPoint = 0;
		final String[] postFixes = new String[] {"bytes", "kb", "mb", "gb", "pb", "eb"};
		while (byteCount >= 1000) {
			index ++;
			//no need to round -- this doesn't have to be
			//perfect
			postDecimalPoint = (int)(byteCount % 1000);
			byteCount /= 1000;
		}

		return Long.toString(byteCount) +
				"." + Integer.toString(postDecimalPoint) +
				" " + postFixes[index];
	}
}