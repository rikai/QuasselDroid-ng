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

package de.kuschku.util.irc.chanmodes.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.kuschku.util.irc.chanmodes.AbstractIrcModeProvider;
import de.kuschku.util.irc.chanmodes.ChanMode;

import static de.kuschku.util.irc.chanmodes.ChanMode.ALLOW_INVITE;
import static de.kuschku.util.irc.chanmodes.ChanMode.ANTIFLOOD;
import static de.kuschku.util.irc.chanmodes.ChanMode.AUDITORIUM;
import static de.kuschku.util.irc.chanmodes.ChanMode.BAN;
import static de.kuschku.util.irc.chanmodes.ChanMode.BAN_EXCEPTION;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_CAPS;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_COLOR;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_CTCP;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_EXTERNAL;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_KICK;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_KNOCK;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_NICKCHANGE;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_NOTICE;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_UNIDENTIFIED;
import static de.kuschku.util.irc.chanmodes.ChanMode.CENSOR;
import static de.kuschku.util.irc.chanmodes.ChanMode.FORWARD;
import static de.kuschku.util.irc.chanmodes.ChanMode.HIDE_JOINS;
import static de.kuschku.util.irc.chanmodes.ChanMode.INVITE_EXCEPTION;
import static de.kuschku.util.irc.chanmodes.ChanMode.JOIN_THROTTLE;
import static de.kuschku.util.irc.chanmodes.ChanMode.LIMIT;
import static de.kuschku.util.irc.chanmodes.ChanMode.MODERATED;
import static de.kuschku.util.irc.chanmodes.ChanMode.ONLY_INVITE;
import static de.kuschku.util.irc.chanmodes.ChanMode.ONLY_OPER;
import static de.kuschku.util.irc.chanmodes.ChanMode.ONLY_SSL;
import static de.kuschku.util.irc.chanmodes.ChanMode.PARANOID;
import static de.kuschku.util.irc.chanmodes.ChanMode.PASSWORD;
import static de.kuschku.util.irc.chanmodes.ChanMode.PERMANENT;
import static de.kuschku.util.irc.chanmodes.ChanMode.QUIET_UNIDENTIFIED;
import static de.kuschku.util.irc.chanmodes.ChanMode.REGISTERED;
import static de.kuschku.util.irc.chanmodes.ChanMode.RESTRICT_TOPIC;
import static de.kuschku.util.irc.chanmodes.ChanMode.STRIP_COLOR;
import static de.kuschku.util.irc.chanmodes.ChanMode.UNLISTED;

public class InspireIrcModeProvider extends AbstractIrcModeProvider {

    protected final Set<Character> supportedModes = new HashSet<>(Arrays.asList(
            'A', 'B', 'C', 'D', 'G', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'c', 'f', 'i', 'j', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't', 'u', 'z'
    ));

    @Override
    public ChanMode modeFromChar(char mode) {
        switch (mode) {
            case 'A':
                return ALLOW_INVITE;
            case 'B':
                return BLOCK_CAPS;
            case 'C':
                return BLOCK_CTCP;
            case 'D':
                return HIDE_JOINS;
            case 'G':
                return CENSOR;
            case 'K':
                return BLOCK_KNOCK;
            case 'L':
                return FORWARD;
            case 'M':
                return QUIET_UNIDENTIFIED;
            case 'N':
                return BLOCK_NICKCHANGE;
            case 'O':
                return ONLY_OPER;
            case 'P':
                return PERMANENT;
            case 'Q':
                return BLOCK_KICK;
            case 'R':
                return BLOCK_UNIDENTIFIED;
            case 'S':
                return STRIP_COLOR;
            case 'T':
                return BLOCK_NOTICE;
            case 'c':
                return BLOCK_COLOR;
            case 'f':
                return ANTIFLOOD;
            case 'i':
                return ONLY_INVITE;
            case 'j':
                return JOIN_THROTTLE;
            case 'k':
                return PASSWORD;
            case 'l':
                return LIMIT;
            case 'm':
                return MODERATED;
            case 'n':
                return BLOCK_EXTERNAL;
            case 'p':
                return PARANOID;
            case 'r':
                return REGISTERED;
            case 's':
                return UNLISTED;
            case 't':
                return RESTRICT_TOPIC;
            case 'u':
                return AUDITORIUM;
            case 'z':
                return ONLY_SSL;

            case 'b':
                return BAN;
            case 'e':
                return BAN_EXCEPTION;
            case 'I':
                return INVITE_EXCEPTION;
        }
        return null;
    }

    @Override
    public char charFromMode(ChanMode mode) {
        switch (mode) {
            case ALLOW_INVITE:
                return 'A';
            case BLOCK_CAPS:
                return 'B';
            case BLOCK_CTCP:
                return 'C';
            case HIDE_JOINS:
                return 'D';
            case CENSOR:
                return 'G';
            case BLOCK_KNOCK:
                return 'K';
            case FORWARD:
                return 'L';
            case QUIET_UNIDENTIFIED:
                return 'M';
            case BLOCK_NICKCHANGE:
                return 'N';
            case ONLY_OPER:
                return 'O';
            case PERMANENT:
                return 'P';
            case BLOCK_KICK:
                return 'Q';
            case BLOCK_UNIDENTIFIED:
                return 'R';
            case STRIP_COLOR:
                return 'S';
            case BLOCK_NOTICE:
                return 'T';
            case BLOCK_COLOR:
                return 'c';
            case ANTIFLOOD:
                return 'f';
            case ONLY_INVITE:
                return 'i';
            case JOIN_THROTTLE:
                return 'j';
            case PASSWORD:
                return 'k';
            case LIMIT:
                return 'l';
            case MODERATED:
                return 'm';
            case BLOCK_EXTERNAL:
                return 'n';
            case PARANOID:
                return 'p';
            case REGISTERED:
                return 'r';
            case UNLISTED:
                return 's';
            case RESTRICT_TOPIC:
                return 't';
            case AUDITORIUM:
                return 'u';
            case ONLY_SSL:
                return 'z';
        }
        return ' ';
    }

    @Override
    protected Collection<Character> supportedModes() {
        return supportedModes;
    }
}
