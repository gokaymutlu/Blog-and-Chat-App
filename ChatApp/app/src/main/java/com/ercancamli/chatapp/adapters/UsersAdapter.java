package com.ercancamli.chatapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ercancamli.chatapp.databinding.ItemContainerUserBinding;
import com.ercancamli.chatapp.listeners.UserListener;
import com.ercancamli.chatapp.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private final List<User> user;
    private final UserListener userListener;

    public UsersAdapter(List<User> users, UserListener userListener) {
        this.user = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater. from(parent.getContext()),
        parent,
         false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserdata(user.get(position));
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
            ItemContainerUserBinding binding;

            UserViewHolder(ItemContainerUserBinding itemContainerUserBinding) {
                super(itemContainerUserBinding.getRoot());
                binding = itemContainerUserBinding;
            }
            void setUserdata(User user) {
                binding.textName.setText(user.name);
                binding.textEmail.setText(user.email);
                binding.imageProfile.setImageBitmap(getUserImage(user.image));
                binding.getRoot().setOnClickListener(view -> userListener.onUserClicked(user));
            }
        }


    private Bitmap getUserImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length);

    }
}