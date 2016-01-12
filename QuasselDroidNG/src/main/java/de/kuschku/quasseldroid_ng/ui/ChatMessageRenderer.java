package de.kuschku.quasseldroid_ng.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.TextUtils;

import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import de.kuschku.libquassel.Client;
import de.kuschku.libquassel.message.Message;
import de.kuschku.quasseldroid_ng.R;
import de.kuschku.quasseldroid_ng.util.DateFormatHelper;
import de.kuschku.quasseldroid_ng.util.IrcFormatHelper;
import de.kuschku.quasseldroid_ng.util.IrcUserUtils;
import de.kuschku.quasseldroid_ng.util.SpanFormatter;
import de.kuschku.quasseldroid_ng.util.ThemeUtil;

public class ChatMessageRenderer {
    private final ThemeUtil themeUtil;
    private final DateTimeFormatter format;
    private final FormatStrings strings;
    private final IrcFormatHelper helper;

    private Client client;
    private boolean fullHostmask = true;

    public ChatMessageRenderer(Context ctx) {
        this.themeUtil = new ThemeUtil(ctx);
        this.format = DateFormatHelper.getTimeFormatter(ctx);
        this.strings = new FormatStrings(ctx);
        this.helper = new IrcFormatHelper(themeUtil.colors);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void setColors(MessageViewHolder holder, boolean highlight, boolean isServerAction) {
        if (highlight) {
            applyStyle(holder,
                    themeUtil.colors.colorForegroundHighlight,
                    Typeface.NORMAL,
                    themeUtil.colors.colorForegroundHighlight,
                    themeUtil.colors.colorBackgroundHighlight
            );
        } else if (isServerAction) {
            applyStyle(holder,
                    themeUtil.colors.colorForegroundSecondary,
                    Typeface.ITALIC,
                    themeUtil.colors.colorForegroundSecondary,
                    themeUtil.colors.colorBackgroundSecondary
            );
        } else {
            applyStyle(holder,
                    themeUtil.colors.colorForeground,
                    Typeface.NORMAL,
                    themeUtil.colors.colorForegroundSecondary,
                    themeUtil.colors.transparent
            );
        }
    }

    private void applyStyle(MessageViewHolder holder, @ColorInt int textColor, int style, @ColorInt int timeColor, @ColorInt int bgColor) {
        holder.content.setTextColor(textColor);
        holder.content.setTypeface(null, style);
        holder.time.setTextColor(timeColor);
        holder.itemView.setBackgroundColor(bgColor);
    }

    private CharSequence formatNick(String hostmask, boolean full) {
        CharSequence formattedNick = helper.formatUserNick(IrcUserUtils.getNick(hostmask));
        if (full) {
            return strings.formatUsername(formattedNick, IrcUserUtils.getMask(hostmask));
        } else {
            return formattedNick;
        }
    }

    private CharSequence formatNick(String hostmask) {
        return formatNick(hostmask, fullHostmask);
    }

    public void onBindPlain(MessageViewHolder holder, Message message) {
        holder.content.setText(
                strings.formatPlain(
                        formatNick(message.sender, false),
                        helper.formatIrcMessage(message.content)
                )
        );
    }

    public void onBindNotice(MessageViewHolder holder, Message message) {
        holder.content.setText(strings.formatAction(
                formatNick(message.sender, false),
                helper.formatIrcMessage(message.content)
        ));
    }

    public void onBindAction(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindNick(MessageViewHolder holder, Message message) {
        if (message.flags.Self)
            holder.content.setText(strings.formatNick(
                    formatNick(message.sender, false)
            ));
        else
            holder.content.setText(strings.formatNick(
                    formatNick(message.sender, false),
                    helper.formatUserNick(message.content)
            ));
    }

    public void onBindMode(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindJoin(MessageViewHolder holder, Message message) {
        holder.content.setText(strings.formatJoin(
                formatNick(message.sender),
                client.getBuffer(message.bufferInfo.id).getName()
        ));
    }

    public void onBindPart(MessageViewHolder holder, Message message) {
        holder.content.setText(strings.formatPart(
                formatNick(message.sender),
                client.getBuffer(message.bufferInfo.id).getName(),
                message.content
        ));
    }

    public void onBindQuit(MessageViewHolder holder, Message message) {
        holder.content.setText(strings.formatQuit(
                formatNick(message.sender),
                message.content
        ));
    }

    public void onBindKick(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindKill(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindServer(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindInfo(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindError(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindDayChange(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindTopic(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindNetsplitJoin(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindNetsplitQuit(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }

    public void onBindInvite(MessageViewHolder holder, Message message) {
        holder.content.setText(message.toString());
    }
    
    public void onBind(MessageViewHolder holder, Message message) {
        holder.time.setText(format.print(message.time));
        switch (message.type) {
            case Plain:
                setColors(holder, message.flags.Highlight, false);
                onBindPlain(holder, message);
                break;
            case Notice:
                setColors(holder, message.flags.Highlight, false);
                onBindNotice(holder, message);
                break;
            case Action:
                setColors(holder, message.flags.Highlight, false);
                onBindAction(holder, message);
                break;
            case Nick:
                setColors(holder, message.flags.Highlight, true);
                onBindNick(holder, message);
                break;
            case Mode:
                setColors(holder, message.flags.Highlight, true);
                onBindMode(holder, message);
                break;
            case Join:
                setColors(holder, message.flags.Highlight, true);
                onBindJoin(holder, message);
                break;
            case Part:
                setColors(holder, message.flags.Highlight, true);
                onBindPart(holder, message);
                break;
            case Quit:
                setColors(holder, message.flags.Highlight, true);
                onBindQuit(holder, message);
                break;
            case Kick:
                setColors(holder, message.flags.Highlight, true);
                onBindKick(holder, message);
                break;
            case Kill:
                setColors(holder, message.flags.Highlight, true);
                onBindKill(holder, message);
                break;
            case Server:
                setColors(holder, message.flags.Highlight, false);
                onBindServer(holder, message);
                break;
            case Info:
                setColors(holder, message.flags.Highlight, false);
                onBindInfo(holder, message);
                break;
            case Error:
                setColors(holder, message.flags.Highlight, false);
                onBindError(holder, message);
                break;
            case DayChange:
                setColors(holder, message.flags.Highlight, true);
                onBindDayChange(holder, message);
                break;
            case Topic:
                setColors(holder, message.flags.Highlight, true);
                onBindTopic(holder, message);
                break;
            case NetsplitJoin:
                setColors(holder, message.flags.Highlight, true);
                onBindNetsplitJoin(holder, message);
                break;
            case NetsplitQuit:
                setColors(holder, message.flags.Highlight, true);
                onBindNetsplitQuit(holder, message);
                break;
            case Invite:
                setColors(holder, message.flags.Highlight, true);
                onBindInvite(holder, message);
                break;
        }
    }

    private static class FormatStrings {
        private final String username_hostmask;

        private final String message_plain;
        private final String message_join;
        private final String message_part;
        private final String message_part_extra;
        private final String message_quit;
        private final String message_quit_extra;
        private final String message_kill;
        private final String message_kick;
        private final String message_kick_extra;
        private final String message_mode;
        private final String message_nick_self;
        private final String message_nick_other;
        private final String message_daychange;
        private final String message_action;

        public FormatStrings(Context ctx) {
            username_hostmask = ctx.getString(R.string.username_hostmask);

            message_plain = ctx.getString(R.string.message_plain);
            message_join = ctx.getString(R.string.message_join);
            message_part = ctx.getString(R.string.message_part);
            message_part_extra = ctx.getString(R.string.message_part_extra);
            message_quit = ctx.getString(R.string.message_quit);
            message_quit_extra = ctx.getString(R.string.message_quit_extra);
            message_kill = ctx.getString(R.string.message_kill);
            message_kick = ctx.getString(R.string.message_kick);
            message_kick_extra = ctx.getString(R.string.message_kick_extra);
            message_mode = ctx.getString(R.string.message_mode);
            message_nick_self = ctx.getString(R.string.message_nick_self);
            message_nick_other = ctx.getString(R.string.message_nick_other);
            message_daychange = ctx.getString(R.string.message_daychange);
            message_action = ctx.getString(R.string.message_action);
        }

        public CharSequence formatUsername(CharSequence nick, CharSequence hostmask) {
            return SpanFormatter.format(username_hostmask, nick, hostmask);
        }

        public CharSequence formatJoin(CharSequence user, CharSequence channel) {
            return SpanFormatter.format(message_join, user, channel);
        }

        public CharSequence formatPart(CharSequence user, CharSequence channel) {
            return SpanFormatter.format(message_part, user, channel);
        }
        public CharSequence formatPart(CharSequence user, CharSequence channel, CharSequence reason) {
            if (reason == null || reason.length() == 0) return formatPart(user, channel);

            return SpanFormatter.format(message_part_extra, user, channel, reason);
        }

        public CharSequence formatQuit(CharSequence user) {
            return SpanFormatter.format(message_quit, user);
        }
        public CharSequence formatQuit(CharSequence user, CharSequence reason) {
            if (reason == null || reason.length() == 0) return formatQuit(user);

            return SpanFormatter.format(message_quit_extra, user, reason);
        }

        public CharSequence formatKill(CharSequence user, CharSequence channel) {
            return SpanFormatter.format(message_kill, user, channel);
        }

        public CharSequence formatKick(CharSequence user, CharSequence kicked) {
            return SpanFormatter.format(message_kick, user, kicked);
        }
        public CharSequence formatKick(CharSequence user, CharSequence kicked, CharSequence reason) {
            if (reason == null || reason.length() == 0) return formatKick(user, kicked);

            return SpanFormatter.format(message_kick_extra, user, kicked, reason);
        }

        public CharSequence formatMode(CharSequence mode, CharSequence user) {
            return SpanFormatter.format(message_mode, mode, user);
        }

        public CharSequence formatNick(CharSequence newNick) {
            return SpanFormatter.format(message_nick_self, newNick);
        }
        public CharSequence formatNick(CharSequence oldNick, CharSequence newNick) {
            if (newNick == null || newNick.length() == 0) return formatNick(oldNick);

            return SpanFormatter.format(message_nick_other, oldNick, newNick);
        }

        public CharSequence formatDayChange(CharSequence day) {
            return SpanFormatter.format(message_daychange, day);
        }

        public CharSequence formatAction(CharSequence user, CharSequence channel) {
            return SpanFormatter.format(message_action, user, channel);
        }

        public CharSequence formatPlain(CharSequence nick, CharSequence message) {
            return SpanFormatter.format(message_plain, nick, message);
        }
    }
}