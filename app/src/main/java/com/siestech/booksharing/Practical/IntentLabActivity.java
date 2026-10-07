package com.siestech.booksharing.Practical;
import android.content.Intent; import android.net.Uri; import android.os.Bundle; import androidx.appcompat.app.AppCompatActivity; import com.siestech.booksharing.BookDetailsActivity; import com.siestech.booksharing.R;
public class IntentLabActivity extends AppCompatActivity{
 @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_intent_lab);
  findViewById(R.id.explicitBtn).setOnClickListener(v->{Intent i=new Intent(this,BookDetailsActivity.class);i.putExtra("title","The Alchemist");i.putExtra("author","Paulo Coelho");i.putExtra("category","Novel");i.putExtra("description","A story about dreams and courage.");i.putExtra("ownerName","Shreya");i.putExtra("condition","Good");i.putExtra("imageName","book1");i.putExtra("available",true);i.putExtra("ownerId","demo-shreya");startActivity(i);});
  findViewById(R.id.shareBtn).setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_SEND);i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,"BorrowBox — The Alchemist by Paulo Coelho");startActivity(Intent.createChooser(i,"Share with"));});
  findViewById(R.id.webBtn).setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/"));startActivity(i);});
  findViewById(R.id.emailBtn).setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"));i.putExtra(Intent.EXTRA_SUBJECT,"BorrowBox enquiry");i.putExtra(Intent.EXTRA_TEXT,"Hello BorrowBox team");startActivity(i);});
  findViewById(R.id.shareReceivedBtn).setOnClickListener(v->startActivity(new Intent(this,ShareTextActivity.class)));
 }
}
