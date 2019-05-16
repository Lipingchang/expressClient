package com.example.expressclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.expressclient.viewhelper.CardItem;
import com.example.expressclient.viewhelper.CardPagerAdapter;
import com.example.expressclient.viewhelper.ShadowTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class nav_home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListenerNavHome mListener;
    private View view;
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_nav_home, container, false);
        convenientBanner = view.findViewById(R.id.convenientBanner);
        ArrayList<Integer> bannerImages = new ArrayList<>();
        for (int position = 1; position <= 3; position++){
            int i = 0;
            try{
                Field idField = R.drawable.class.getDeclaredField("banner"+position);
                i = idField.getInt(idField);
            }catch (Exception e){
                continue;
            }
            bannerImages.add( i );
        }
        convenientBanner.setPages(new CBViewHolderCreator(){
            @Override
            public Holder createHolder(View itemView) {
                return new BannerHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.banner;
            }
        },bannerImages).setPageIndicator(new int[]{R.drawable.ic_page_indicator,R.drawable.ic_page_indicator_focused})
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_LEFT)
        ;

        viewPager = view.findViewById(R.id.OrderViewpager);
        CardPagerAdapter mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1));

        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false,mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);


        return view;
    }
    public class BannerHolder extends Holder<Integer>{
        private ImageView imageView;
        public BannerHolder(View view){
            super(view);
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.ivPost);
        }

        @Override
        public void updateUI(Integer data) {
            imageView.setImageResource(data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListenerNavHome) {
            mListener = (OnFragmentInteractionListenerNavHome) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListenerNavUserCenter");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListenerNavHome {
        void onFragmentInteraction(Uri uri);
    }

}
