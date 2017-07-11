package mx.infotec.dads.sekc.config.dbmigrations;

import mx.infotec.dads.sekc.domain.Authority;
import mx.infotec.dads.sekc.domain.User;
import mx.infotec.dads.sekc.security.AuthoritiesConstants;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;

import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import mx.infotec.dads.essence.util.EssenceMapping;
/**
 * Creates the initial database setup
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addUsers")
    public void addUsers(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);

        User systemUser = new User();
        systemUser.setId("user-0");
        systemUser.setLogin("system");
        systemUser.setPassword("$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG");
        systemUser.setFirstName("");
        systemUser.setLastName("System");
        systemUser.setEmail("system@localhost");
        systemUser.setActivated(true);
        systemUser.setLangKey("en");
        systemUser.setCreatedBy(systemUser.getLogin());
        systemUser.setCreatedDate(ZonedDateTime.now());
        systemUser.getAuthorities().add(adminAuthority);
        systemUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(systemUser);

        User anonymousUser = new User();
        anonymousUser.setId("user-1");
        anonymousUser.setLogin("anonymoususer");
        anonymousUser.setPassword("$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO");
        anonymousUser.setFirstName("Anonymous");
        anonymousUser.setLastName("User");
        anonymousUser.setEmail("anonymous@localhost");
        anonymousUser.setActivated(true);
        anonymousUser.setLangKey("en");
        anonymousUser.setCreatedBy(systemUser.getLogin());
        anonymousUser.setCreatedDate(ZonedDateTime.now());
        mongoTemplate.save(anonymousUser);

        User adminUser = new User();
        adminUser.setId("user-2");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("en");
        adminUser.setCreatedBy(systemUser.getLogin());
        adminUser.setCreatedDate(ZonedDateTime.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(adminUser);

        User userUser = new User();
        userUser.setId("user-3");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("");
        userUser.setLastName("User");
        userUser.setEmail("user@localhost");
        userUser.setActivated(true);
        userUser.setLangKey("en");
        userUser.setCreatedBy(systemUser.getLogin());
        userUser.setCreatedDate(ZonedDateTime.now());
        userUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(userUser);
    }
    
    @ChangeSet(order = "03", author = "initiator", id = "03-createAlphasStatesCheckPointsCatalogs")
    public void createAlphasStatesCheckPointsCatalogs(MongoTemplate mongoTemplate) {
        
        String[] alphas = {
        "Stakeholders", "Oportunnity", "Software System",
        "Work", "Way of Working", "Team", "Requirements"
        };

        String[][] states = {{
            "Recognized",
            "Represented",
            "Involved",
            "In Agreement",
            "Satisfied for Deployment",
            "Satisfied in Use"
        }, {
            "Identified",
            "Solution Needed",
            "Value Established",
            "Viable",
            "Addressed",
            "Benefit Accrued"
        }, {
            "Architecture Selected",
            "Demonstrable",
            "Usable",
            "Ready",
            "Operational",
            "Retired"
        }, {
            "Initiated",
            "Prepared",
            "Started",
            "Under Control",
            "Concluded",
            "Closed"
        }, {
            "Principles Established",
            "Foundation Established",
            "In Use",
            "In Place",
            "Working Well",
            "Retired"
        }, {
            "Seeded",
            "Formed",
            "Collaborating",
            "Performing",
            "Adjourned"
        }, {
            "Conceived",
            "Bounded",
            "Coherent",
            "Acceptable",
            "Addressed",
            "Fulfilled"
        }};
        String[][][] checklists = {{{
            "Stakeholders groups identified",
            "Key stakeholder groups represented",
            "Responsibilities defined"
        }, {
            "Responsibilities agreed",
            "Representatives authorized",
            "Collaboration approach agreed",
            "Way of working supported & respected"
        }, {
            "Representatives assist the team",
            "Timely feedback and decisions provided",
            "Changes promptly communicated"
        }, {
            "Minimal expectations agreed",
            "Rep's happy with their involvement",
            "Rep's input valued",
            "Team's input valued",
            "Priorities clear & perspectives balanced"
        }, {
            "Stakeholder feedback provided",
            "System ready for deployment"
        }, {
            "Feedback on system use available",
            "System meets expectations"
        },}, {{
            "Idea behind opportunity identified",
            "At least one investing stakeholder interested",
            "Other stakeholders identified"
        }, {
            "Solution identified",
            "Stakeholder's needs established",
            "Problems and root causes identified",
            "Need for a solution confirmed",
            "At least one solution proposed"
        }, {
            "Opportunity value quantified",
            "Solution impact understood",
            "System value understood",
            "Success criteria clear",
            "Outcomes clear and quantified"
        }, {
            "Solution outlined",
            "Solution possible within constraints",
            "Risk acceptable & manageable",
            "Solution profitable",
            "Reasons to develop solution understood",
            "Pursuit viable"
        }, {
            "Opportunity addressed",
            "Solution worth deploying",
            "Stakeholders satisfied"
        }, {
            "Solution accrues benefits",
            "ROI acceptable"
        }
        }, {{
            "Architecture selection criteria agrees",
            "HW platforms identified",
            "Technologies selected",
            "System boundary known ",
            "Decisions on system organization made",
            "Buy, build, reuse decisions made",
            "Key technical risks agreed to"
        }, {
            "Key architectural characteristics demonstrated",
            "System exercised & performance measured",
            "Critical HW configurations demonstrated",
            "Critical interfaces demonstrated",
            "Integration with enviroment monstrated",
            "Architecture accepted as fit-for-purpose"
        }, {
            "System can be operated",
            "System functionality tested",
            "System performance acceptable",
            "Defect levels acceptable",
            "System fully documented",
            "Release content known",
            "Added value clear"
        }, {
            "User documentation available",
            "System accepted as fit-for-purpose",
            "Stakeholders want the system",
            "Operational support in place"
        }, {
            "System available for use",
            "System live",
            "Agreed service levels supported"
        }, {
            "Replaced or discontinued",
            "No longer supported",
            "No authorized users",
            "Updates stopped"
        }
        }, {{
            "Required result clear",
            "Constraints clear",
            "Funding stakeholders known",
            "Initiator identified",
            "Accepting stakeholders known",
            "Source of funding clear",
            "Priority clear"
        }, {
            "Commitment made",
            "Cost and effort estimated",
            "Resource availability understood",
            "Risk exposure understood",
            "Acceptance criteria established",
            "Sufficiently broken down to start",
            "Tasks identified and prioritized",
            "Credible plan in place",
            "Funding in place",
            "At least one team member ready",
            "Integration points defined"
        }, {
            "Development started",
            "Progress monitored",
            "Definition of done in place",
            "Tasks being progressed"
        }, {
            "Tasks being completed",
            "Unplanned work under control",
            "Risks under control",
            "Estimates revised to reflect performance",
            "Progress measured",
            "Re-work under control",
            "Commitments consistently meet"
        }, {
            "Only admin tasks left",
            "Results achieved",
            "Resulting system accepted"
        }, {
            "Lessons learned",
            "Metrics available",
            "Everything archived",
            "Budget reconciled & closed",
            "Team released",
            "No outstanding, uncompleted tasks"
        }
        }, {{
            "Team actively support principles",
            "Stakeholders agree with principles",
            "Tool needs agreed",
            "Approach recommended",
            "Operational context understood",
            "Practice & tool constraints known"
        }, {
            "Key practices & tools selected",
            "Practices needed to start work agrees",
            "Non-negotiable practices & tools identified",
            "Gaps between available and needed way of working understood",
            "Gaps in capability understood",
            "Integrated way of working available"
        }, {
            "Practices & tools in use",
            "Regularly inspected",
            "Adapted to context",
            "Supported by team",
            "Feedback mechanisms in place",
            "Practices & tools support collaboration"
        }, {
            "Used by whole team",
            "Accessible to whole team",
            "Inspected and adapted by whole team"
        }, {
            "Predictable progress being made",
            "Practices naturally applied",
            "Tools naturally support way-of-working",
            "Continually tuned"
        }, {
            "No longer in use",
            "Lessons learned shared"
        }
        }, {{
            "Mission defined",
            "Constraints known and defined",
            "Growth mechanisms in place",
            "Composition defined",
            "Responsibilitied outlined",
            "Required commitment level clear",
            "Required competencies identified",
            "Size determined",
            "Governance rules defines",
            "Leadership model selected"
        }, {
            "Enough members recruited",
            "Roles understood",
            "How to work understood",
            "Members introduced",
            "Individual responsibilities accepted and aligned to competencies",
            "Members accepting work",
            "External collaborators identified",
            "Communication mechanisms defined",
            "Members commit to team"
        }, {
            "Works as one unit",
            "Communication open and honest",
            "Focused on mission",
            "Members know each other"
        }, {
            "Consistently meeting commitments",
            "Continuosly adapting to change",
            "Addresses problems",
            "Rework and backtracking minimized",
            "Waste continuously eliminated"
        }, {
            "Responsibilities fulfilled",
            "Members available to other teams",
            "Mission concluded"
        }
        }, {{
            "Stakeholders agree system is to be produces",
            "Users identified",
            "Funding stakeholders identified",
            "Opportunity clear"
        }, {
            "Development stakeholders identified",
            "System purpose agreed",
            "System success clear",
            "Shared solution understanding exists",
            "Requirements management in place",
            "Prioritization scheme clear",
            "Constraints identified & considered",
            "Assumptions clear"
        }, {
            "Requirements shared",
            "Requirements' origin clear",
            "Rationale clear",
            "Conflicts addressed",
            "Essential characteristics clear",
            "Key usage scenarios explained",
            "Priorities clear",
            "Impact understood",
            "Team knows & agrees on what to deliver"
        }, {
            "Acceotable solution described",
            "Change under control",
            "Value to be realized clear",
            "Clear how opportunity addressed",
            "Testable"
        }, {
            "Enough addressed to be acceptable",
            "Requirements and system match",
            "Value realized clear",
            "System worth making operational"
        }, {
            "Stakeholders accept requirements",
            "No hindering requirements",
            "Requirements fully satisfied"
        }
        }
        };
        
        for (int i = 0; i < alphas.length; i++) {
            SEAlpha alpha = new SEAlpha();
        
            EssenceMapping.fillSELanguageElements(alpha);
            EssenceMapping.fillBasicElement(alpha);
            alpha.setName( alphas[i] );
            alpha.setStates(new ArrayList<>());
            mongoTemplate.save(alpha);
            for (int j = 0; j < states[i].length; j++) {
                SEState state = new SEState();
                EssenceMapping.fillSELanguageElements(state);
                state.setName( states[i][j] );
                state.setAlpha(alpha);
                state.setCheckListItem(new ArrayList<>());
                mongoTemplate.save(state);
                alpha.getStates().add(state);
                for (int k = 0; k < checklists[i][j].length; k++) {
                    SECheckpoint checkpoint = new SECheckpoint();
                    EssenceMapping.fillSELanguageElements(checkpoint);
                    EssenceMapping.fillCheckpoint(checkpoint, state);
                    checkpoint.setName( checklists[i][j][k] );
                    mongoTemplate.save(checkpoint);
                    state.getCheckListItem().add(checkpoint);
                }
                //update with SECheckpoint list
                mongoTemplate.save(state);
            }
            //update with SEState list
            mongoTemplate.save(alpha);
        }       
    }
}
