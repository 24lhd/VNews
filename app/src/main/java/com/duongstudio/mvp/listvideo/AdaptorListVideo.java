package com.duongstudio.mvp.listvideo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duongstudio.mvp.main.MainActivity;
import com.duongstudio.obj.AdaptorADS;
import com.duongstudio.obj.ItemVideo;
import com.duongstudio.videotintuc.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView;

import java.util.List;

/**
 * Created by D on 8/11/2017.
 */

public class AdaptorListVideo extends AdaptorADS {


    private List<Object> listObject;
    private MainActivity mainActivity;
    private Context context;

    public AdaptorListVideo(RecyclerView recyclerView, List<Object> listObject, Object doiTuongCanThem, int viTriThem, Context context) {
        super(recyclerView, listObject, doiTuongCanThem, viTriThem, context);
        this.context = context;
        this.mainActivity = ((MainActivity) context);
        this.listObject = listObject;
    }

    class ItemVideoHoder extends ViewHolderItem {
        ImageView imvThumVideo;
        TextView tvTitle;
        TextView tvTimeVideo;
        TextView tvTimeDate;
        LinearLayout itemCard;

        public ItemVideoHoder(View itemView) {
            super(itemView);
            imvThumVideo = itemView.findViewById(R.id.card_video_img_thumb_video);
            tvTitle = itemView.findViewById(R.id.card_video_txt_title);
            tvTimeVideo = itemView.findViewById(R.id.card_video_txt_time_video);
            itemCard = itemView.findViewById(R.id.card_video_layout_item);
            tvTimeDate = itemView.findViewById(R.id.card_video_txt_name_cate_time);
//            itemCard.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mainActivity.s);
//                }
//            });
        }
    }

    class FBAds extends ViewHolderAds {
        NativeAd nativeAd;

        public FBAds(final View itemView) {
            super(itemView);
            loadAds();
        }

        private void loadAds() {
            nativeAd = new NativeAd(context, context.getResources().getString(R.string.facebook_ads_id_in_list_video));
            nativeAd.setAdListener(new AdListener() {

                @Override
                public void onError(Ad ad, AdError error) {
                    // Ad error callback
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    View adView = NativeAdView.render(context, nativeAd, NativeAdView.Type.HEIGHT_300);
                    LinearLayout nativeAdContainer = (LinearLayout) itemView.findViewById(R.id.native_ad_container_in_list);
                    nativeAdContainer.addView(adView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });

            // Request an ad
            nativeAd.loadAd();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ADS:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_ads_fb_in_list, parent, false);
                return new FBAds(nativeExpressLayoutView);//new NativeExpressAdViewHolder(nativeExpressLayoutView);
            default:
            case ITEM:
                View menuItemLayouthView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_video, parent, false);
                return new ItemVideoHoder(menuItemLayouthView);
        }
    }

    @Override
    public void ViewHolderAds(ViewHolderAds viewHolder, int position) {

    }

    @Override
    public void ViewHolderItem(ViewHolderItem viewHolder, int position) {
        ItemVideoHoder itemVideoHoder = (ItemVideoHoder) viewHolder;
        final ItemVideo itemVideo = ((ItemVideo) listObject.get(position));
        try {
            itemVideoHoder.tvTitle.setText(itemVideo.getTitle());
            itemVideoHoder.tvTimeDate.setText(itemVideo.getPubDate().getGio()
                    + ":" + itemVideo.getPubDate().getPhut()
                    + " " + itemVideo.getPubDate().getNgay()
                    + "/" + itemVideo.getPubDate().getThang()
                    + "/" + itemVideo.getPubDate().getNam());
            itemVideoHoder.tvTimeVideo.setText(itemVideo.getTimeVideo());
            Glide.with(mainActivity).load(itemVideo.getImage()).into(itemVideoHoder.imvThumVideo);
            itemVideoHoder.itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.startViewVideo(itemVideo);
                }
            });
        } catch (Exception e) {
        }

    }

}
