/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2019 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.action.change.change;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.maddyhome.idea.vim.VimPlugin;
import com.maddyhome.idea.vim.action.VimCommandAction;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.command.CommandFlags;
import com.maddyhome.idea.vim.command.MappingMode;
import com.maddyhome.idea.vim.handler.VisualOperatorActionHandler;
import com.maddyhome.idea.vim.helper.CharacterHelper;
import com.maddyhome.idea.vim.helper.UtilsKt;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author vlan
 */
public class ChangeCaseLowerVisualAction extends VimCommandAction {
  public ChangeCaseLowerVisualAction() {
    super(new VisualOperatorActionHandler() {
      @Override
      protected boolean executeCharacterAndLinewise(@NotNull Editor editor,
                                                    @NotNull Caret caret,
                                                    @NotNull DataContext context,
                                                    @NotNull Command cmd,
                                                    @NotNull RangeMarker range) {
        final Editor topLevelEditor = InjectedLanguageUtil.getTopLevelEditor(editor);
        return VimPlugin.getChange()
          .changeCaseRange(topLevelEditor, caret, UtilsKt.getVimTextRange(range), CharacterHelper.CASE_LOWER);
      }

      @Override
      protected boolean executeBlockwise(@NotNull Editor editor,
                                         @NotNull DataContext context,
                                         @NotNull Command cmd,
                                         @NotNull Map<Caret, ? extends RangeMarker> ranges) {
        final Editor topLevelEditor = InjectedLanguageUtil.getTopLevelEditor(editor);
        return VimPlugin.getChange()
          .changeCaseRange(topLevelEditor, editor.getCaretModel().getPrimaryCaret(), UtilsKt.getVimTextRange(ranges),
                           CharacterHelper.CASE_LOWER);
      }
    });
  }

  @NotNull
  @Override
  public Set<MappingMode> getMappingModes() {
    return MappingMode.V;
  }

  @NotNull
  @Override
  public Set<List<KeyStroke>> getKeyStrokesSet() {
    return parseKeysSet("u");
  }

  @NotNull
  @Override
  public Command.Type getType() {
    return Command.Type.CHANGE;
  }

  @Override
  public EnumSet<CommandFlags> getFlags() {
    return EnumSet.of(CommandFlags.FLAG_EXIT_VISUAL);
  }
}
