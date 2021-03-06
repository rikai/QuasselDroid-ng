/*
 * QuasselDroid - Quassel client for Android
 * Copyright (C) 2016 Janne Koschinski
 * Copyright (C) 2016 Ken Børge Viktil
 * Copyright (C) 2016 Magnus Fjell
 * Copyright (C) 2016 Martin Sandsmark <martin.sandsmark@kde.org>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.kuschku.quasseldroid_ng.ui.chat.chatview;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.kuschku.quasseldroid_ng.R;
import de.kuschku.quasseldroid_ng.ui.theme.AppContext;

@UiThread
public class MessageViewHolder extends RecyclerView.ViewHolder {
    @SuppressWarnings("NullableProblems")
    @NonNull
    @Bind(R.id.time)
    TextView time;

    @SuppressWarnings("NullableProblems")
    @NonNull
    @Bind(R.id.content)
    TextView content;

    public MessageViewHolder(@NonNull AppContext context, @NonNull View itemView, boolean highlightFlag) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        content.setMovementMethod(LinkMovementMethod.getInstance());

        if (highlightFlag) {
            itemView.setBackgroundColor(context.themeUtil().res.colorBackgroundHighlight);
            content.setTextColor(context.themeUtil().res.colorForegroundHighlight);
            time.setTextColor(context.themeUtil().res.colorForegroundHighlight);
        }
    }
}
