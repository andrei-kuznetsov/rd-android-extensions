package org.intellij.android.cwm

import com.intellij.openapi.extensions.ExtensionNotApplicableException
import com.jetbrains.rd.actions.AccessRequirements
import com.jetbrains.rd.ide.model.AddToGroupRuleModel
import com.jetbrains.rd.platform.codeWithMe.permissions.RunAccessLevel
import com.jetbrains.rdserver.actions.BackendDelegatingActionIdsProvider
import com.jetbrains.rdserver.core.GuestProjectSession
import com.jetbrains.rdserver.unattendedHost.UnattendedHostManager

class AndroidUnattendedBackendDelegatingActionIdsProvider : BackendDelegatingActionIdsProvider {

    init {
        if (!UnattendedHostManager.getInstance().isUnattendedMode) {
            throw ExtensionNotApplicableException.create()
        }
    }

    private val UNATTENDED_ACTION_IDS = mapOf(
        // Add explicitly because it contains word "Debug", which is excluded in DefaultBackendDelegatingActionIdsProvider
        "AndroidConnectDebuggerAction" to AccessRequirements.of(RunAccessLevel.FullAccess)
    )

    override fun getAccessRequirements(actionId: String, session: GuestProjectSession): AccessRequirements? {
        return UNATTENDED_ACTION_IDS[actionId]
    }

    override fun getActionIds(session: GuestProjectSession): Collection<String> {
        return UNATTENDED_ACTION_IDS.keys
    }

    override fun getHintParentGroupIds(session: GuestProjectSession): Map<String, Collection<AddToGroupRuleModel>> {
        return emptyMap()
    }

    override fun getEditorActionWithImmediateResultIds(session: GuestProjectSession): Collection<String> {
        return emptyList()
    }
}