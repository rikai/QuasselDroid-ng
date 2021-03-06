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

import static de.kuschku.util.irc.chanmodes.ChanMode.BAN;
import static de.kuschku.util.irc.chanmodes.ChanMode.BAN_EXCEPTION;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_EXTERNAL;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_KICK;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_NICKCHANGE;
import static de.kuschku.util.irc.chanmodes.ChanMode.BLOCK_UNIDENTIFIED;
import static de.kuschku.util.irc.chanmodes.ChanMode.DISABLE_INVITE;
import static de.kuschku.util.irc.chanmodes.ChanMode.INVITE_EXCEPTION;
import static de.kuschku.util.irc.chanmodes.ChanMode.LIMIT;
import static de.kuschku.util.irc.chanmodes.ChanMode.MODERATED;
import static de.kuschku.util.irc.chanmodes.ChanMode.ONLY_INVITE;
import static de.kuschku.util.irc.chanmodes.ChanMode.ONLY_OPER;
import static de.kuschku.util.irc.chanmodes.ChanMode.ONLY_SSL;
import static de.kuschku.util.irc.chanmodes.ChanMode.PASSWORD;
import static de.kuschku.util.irc.chanmodes.ChanMode.PERMANENT;
import static de.kuschku.util.irc.chanmodes.ChanMode.QUIET_UNIDENTIFIED;
import static de.kuschku.util.irc.chanmodes.ChanMode.REGISTERED;
import static de.kuschku.util.irc.chanmodes.ChanMode.RESTRICT_TOPIC;
import static de.kuschku.util.irc.chanmodes.ChanMode.UNLISTED;

public class NeoIrcModeProvider extends AbstractIrcModeProvider {

    protected final Set<Character> supportedModes = new HashSet<>(Arrays.asList(
            'M', 'N', 'O', 'P', 'Q', 'R', 'V', 'i', 'k', 'l', 'm', 'n', 'r', 's', 't', 'z'
    ));

    @Override
    public ChanMode modeFromChar(char mode) {
        switch (mode) {
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
            case 'V':
                return DISABLE_INVITE;
            case 'i':
                return ONLY_INVITE;
            case 'k':
                return PASSWORD;
            case 'l':
                return LIMIT;
            case 'm':
                return MODERATED;
            case 'n':
                return BLOCK_EXTERNAL;
            case 'r':
                return REGISTERED;
            case 's':
                return UNLISTED;
            case 't':
                return RESTRICT_TOPIC;
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
            case DISABLE_INVITE:
                return 'V';
            case ONLY_INVITE:
                return 'i';
            case PASSWORD:
                return 'k';
            case LIMIT:
                return 'l';
            case MODERATED:
                return 'm';
            case BLOCK_EXTERNAL:
                return 'n';
            case REGISTERED:
                return 'r';
            case UNLISTED:
                return 's';
            case RESTRICT_TOPIC:
                return 't';
            case ONLY_SSL:
                return 'z';

            case BAN:
                return 'b';
            case BAN_EXCEPTION:
                return 'e';
            case INVITE_EXCEPTION:
                return 'I';
        }
        return ' ';
    }

    @Override
    protected Collection<Character> supportedModes() {
        return supportedModes;
    }
}
