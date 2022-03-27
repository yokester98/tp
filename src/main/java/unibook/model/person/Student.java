package unibook.model.person;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import unibook.model.module.Module;
import unibook.model.module.ModuleCode;
import unibook.model.module.group.Group;
import unibook.model.tag.Tag;

/**
 * Represents a Student in the UniBook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {

    private final Set<Group> groups;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Set<Tag> tags, Set<Module> modules, Set<Group> groups) {
        super(name, phone, email, tags, modules);
        requireNonNull(groups);
        this.groups = groups;
    }

    /**
     * Another constructor to create a student from a given person and set of groups.
     *
     * @param person person to create student from.
     * @param groups list of groups student is in.
     */
    public Student(Person person, Set<Group> groups) {
        this(person.getName(), person.getPhone(), person.getEmail(),
            person.getTags(), person.getModules(), groups);
    }

    public Student deletePhone() {
        return new Student(getName(), new Phone(), getEmail(), getTags(), getModules(), groups);
    }

    public Student deleteEmail() {
        return new Student(getName(), getPhone(), new Email(), getTags(), getModules(), groups);
    }

    public Student deleteTag(String tagNameToDelete) {
        Set<Tag> tags = getTags();
        Set<Tag> newTags = new HashSet<>();
        for (Tag tag: tags) {
            if (!tag.tagName.equalsIgnoreCase(tagNameToDelete)) {
                newTags.add(tag);
            }
        }
        return new Student(getName(), getPhone(), getEmail(), newTags, getModules(), groups);
    }

    public void addStudentToAllTheirGroups() {
        for (Group group: groups) {
            group.addMember(this);
        }
    }

    public void removeStudentFromAllTheirGroups() {
        for (Group group: groups) {
            group.removeMember(this);
        }
    }


    /**
     * Returns true only if the other student has all the same fields.
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherPerson = (Student) other;

        return super.equals(other);
    }

    /**
     * Returns the groups this student is in.
     *
     * @return
     */
    public Set<Group> getGroups() {
        return groups;
    }

    public void removeGroup(ModuleCode moduleCode, Group group) {
        Set<Group> toBeDeleted = new HashSet<>();
        for (Group g : groups) {
            if (g.sameGroupNameAndModule(moduleCode.toString(), group.getGroupName())) {
                toBeDeleted.add(g);
            }
        }
        for (Group g : toBeDeleted) {
            groups.remove(g);
        }
    }

    public void removeGroup(ModuleCode moduleCode) {
        Set<Group> toBeDeleted = new HashSet<>();
        for (Group g : groups) {
            if (g.getModule().getModuleCode().equals(moduleCode)) {
                toBeDeleted.add(g);
            }
        }
        for (Group g : toBeDeleted) {
            groups.remove(g);
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append("; Phone: ")
            .append(getPhone())
            .append("; Email: ")
            .append(getEmail());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        Set<Module> modules = getModules();
        if (!modules.isEmpty()) {
            builder.append("; Modules: ");
            modules.forEach(builder::append);
        }

        if (!groups.isEmpty()) {
            builder.append("; Groups: ");
            groups.forEach(builder::append);
        }
        return builder.toString();
    }
}
